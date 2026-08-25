package com.generation.giardini.service.preventivo;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.PreventivoDTO;
import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.entity.preventivo.Preventivo;
import com.generation.giardini.entity.preventivo.StatoPreventivo;
import com.generation.giardini.entity.servizio.NomeServizio;
import com.generation.giardini.entity.servizio.Servizio;
import com.generation.giardini.entity.utente.Ruolo;
import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.exception.preventivo.PreventivoCreateException;
import com.generation.giardini.exception.preventivo.PreventivoNotFoundException;
import com.generation.giardini.mapper.PreventivoMapper;
import com.generation.giardini.repository.PreventivoRepository;
import com.generation.giardini.repository.ServizioRepository;
import com.generation.giardini.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PreventivoServiceImpl implements PreventivoService {

    private final PreventivoMapper preventivoMapper;
    private final PreventivoRepository preventivoRepository;
    private final ServizioRepository servizioRepository;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean create(PreventivoDTO dto) {
        if (dto == null) {
            throw new PreventivoCreateException("Impossibile creare il preventivo: il DTO fornito è nullo.");
        }

        try {
            Preventivo entity = preventivoMapper.toEntity(dto);
            preventivoRepository.save(entity);
            return true;
            
        } catch (Exception e) {
            throw new PreventivoCreateException("Errore imprevisto durante la creazione del preventivo.", e);
        }
    }

    @Override
    public boolean createGuestRequest(PreventivoRequestDto request) {
        if (request == null || request.getEmail() == null || request.getEmail().isBlank()) {
            throw new PreventivoCreateException("L'email è obbligatoria per una richiesta anonima.");
        }

        try {
            String email = request.getEmail().trim().toLowerCase();
            Utente utente = utenteRepository.findByEmailIgnoreCase(email).orElseGet(() -> createGuest(request, email));
                BigDecimal superficie = new BigDecimal(request.getDimensioni());
                NomeServizio nomeServizio = NomeServizio.valueOf(request.getServizio());
                Servizio servizio = servizioRepository.findFirstByNomeAndAttivoTrueOrderByPrezzoAlMqAsc(nomeServizio)
                    .orElseThrow(() -> new IllegalArgumentException("Servizio senza prezzo attivo: " + request.getServizio()));

            if (!Boolean.TRUE.equals(utente.getGuest())) {
                utente.setNome(firstName(request.getNome()));
                utente.setCognome(lastName(request.getNome()));
                utente.setTelefono(request.getTelefono());
            }

            Preventivo entity = new Preventivo();
            entity.setUtente(utente);
            entity.setIndirizzo(request.getIndirizzo());
            entity.setSuperficieMq(superficie);
            entity.setCostoStimato(servizio.getPrezzoAlMq().multiply(superficie).setScale(2, java.math.RoundingMode.HALF_UP));
            entity.setDescrizione("Servizio richiesto: " + request.getServizio()
                    + (request.getDettagli() == null || request.getDettagli().isBlank()
                            ? "" : "\n" + request.getDettagli()));
            entity.setDataIntervento(request.getDataIntervento().atStartOfDay());
            entity.setDataEmissione(LocalDate.now());
            entity.setStatoPreventivo(StatoPreventivo.IN_ATTESA);
            preventivoRepository.save(entity);
            return true;
        } catch (Exception e) {
            throw new PreventivoCreateException("Errore imprevisto durante la creazione del preventivo.", e);
        }
    }

    private Utente createGuest(PreventivoRequestDto request, String email) {
        Utente guest = new Utente();
        guest.setNome(firstName(request.getNome()));
        guest.setCognome(lastName(request.getNome()));
        guest.setEmail(email);
        guest.setTelefono(request.getTelefono());
        guest.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));
        guest.setAttivo(false);
        guest.setGuest(true);
        guest.setRuolo(Ruolo.UTENTE);
        return utenteRepository.save(guest);
    }

    private String firstName(String fullName) {
        String value = fullName == null ? "" : fullName.trim();
        return value.isEmpty() ? "Guest" : value.split("\\s+", 2)[0];
    }

    private String lastName(String fullName) {
        String value = fullName == null ? "" : fullName.trim();
        String[] parts = value.isEmpty() ? new String[0] : value.split("\\s+", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreventivoDTO> readAll() {
        List<PreventivoDTO> lista = new ArrayList<>();
        for (Preventivo e : preventivoRepository.findAll()) {
            lista.add(preventivoMapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreventivoDTO> readAllByUtente(Long idUtente) {
        List<PreventivoDTO> lista = new ArrayList<>();
        for (Preventivo e : preventivoRepository.findAll()) {
            // Filtro basato sull'utente associato al preventivo
            if (e.getUtente() != null && e.getUtente().getIdUtente().equals(idUtente)) {
                lista.add(preventivoMapper.toDto(e));
            }
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public PreventivoDTO readById(Long id) {
        Preventivo entity = preventivoRepository.findById(id)
                .orElseThrow(() -> new PreventivoNotFoundException(id));
                
        return preventivoMapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Preventivo entity = preventivoRepository.findById(id)
                .orElseThrow(() -> new PreventivoNotFoundException(id));
                
        entity.setStatoPreventivo(StatoPreventivo.ANNULLATO); //Setto lo stato su ANNULLATO per un preventivo cancellato/eliminato
        preventivoRepository.save(entity);

        return true;
    }

    @Override
    public boolean accept(Long id) {
        Preventivo entity = preventivoRepository.findById(id)
                .orElseThrow(() -> new PreventivoNotFoundException(id));

        if (entity.getStatoPreventivo() == StatoPreventivo.IN_ATTESA) {
            entity.setStatoPreventivo(StatoPreventivo.ACCETTATO);
            preventivoRepository.save(entity);
            return true;
        }

        return false;
    }

    @Override
    public boolean reject(Long id) {
        Preventivo entity = preventivoRepository.findById(id)
                .orElseThrow(() -> new PreventivoNotFoundException(id));

        if (entity.getStatoPreventivo() == StatoPreventivo.IN_ATTESA) {
            entity.setStatoPreventivo(StatoPreventivo.RIFIUTATO);
            preventivoRepository.save(entity);
            return true;
        }

        return false;
    }

    @Override
    public Page<PreventivoDTO> readAll(Pageable pageRequest) {
        Page<Preventivo> pagePreventivi = preventivoRepository.findAll(pageRequest);
        return pagePreventivi.map(preventivoMapper::toDto);
    }
}