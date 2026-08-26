package com.generation.giardini.service.preventivo;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.generation.giardini.service.prenotazione.PrenotazioneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PreventivoServiceImpl implements PreventivoService {

    private final PreventivoMapper preventivoMapper;
    private final PreventivoRepository preventivoRepository;
    private final ServizioRepository servizioRepository;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrenotazioneService prenotazioneService;

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
            throw new PreventivoCreateException("L'email è obbligatoria.");
        }

        try {
            String email = request.getEmail().trim().toLowerCase();
            
            // 1. Recuperiamo o creiamo l'utente
            Utente utente = utenteRepository.findByEmailIgnoreCase(email).orElseGet(() -> createGuest(request, email));
            
            // 2. Aggiorniamo i campi dell'utente
            utente.setNome(request.getNome() != null ? request.getNome().trim() : "");
            utente.setCognome(request.getCognome() != null ? request.getCognome().trim() : "");
            utente.setTelefono(normalizzaTelefono(request.getTelefono()));
            utente.setIndirizzo(request.getIndirizzo());

            log.info(">>> TELEFONO PRIMA DEL SALVATAGGIO UTENTE: " + utente.getTelefono());

            // 3. Salviamo, flushiamo e riassegniamo l'utente per blindare l'istanza
            utente = utenteRepository.saveAndFlush(utente);

            BigDecimal superficie = new BigDecimal(request.getDimensioni());
            NomeServizio nomeServizio = NomeServizio.valueOf(request.getServizio());
            Servizio servizio = servizioRepository.findFirstByNomeAndAttivoTrueOrderByPrezzoAlMqAsc(nomeServizio)
                    .orElseThrow(() -> new IllegalArgumentException("Servizio senza prezzo attivo: " + request.getServizio()));

            Preventivo entity = new Preventivo();
            entity.setUtente(utente); // Usiamo l'utente salvato e sincronizzato
            entity.setIndirizzo(request.getIndirizzo());
            entity.setSuperficieMq(superficie);
            entity.setCostoStimato(servizio.getPrezzoAlMq().multiply(superficie).setScale(2, RoundingMode.HALF_UP));
            entity.setDescrizione("Servizio richiesto: " + request.getServizio()
                    + (request.getDettagli() == null || request.getDettagli().isBlank()
                            ? "" : "\n" + request.getDettagli()));
            
            // CONTROLLO DI SICUREZZA SULLA DATA
            if (request.getDataIntervento() != null) {
                entity.setDataIntervento(request.getDataIntervento().atStartOfDay());
            } else {
                entity.setDataIntervento(LocalDate.now().plusDays(1).atStartOfDay()); // Fallback sicuro
            }

            entity.setDataEmissione(LocalDate.now());
            entity.setStatoPreventivo(StatoPreventivo.IN_ATTESA);
            
            preventivoRepository.save(entity);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new PreventivoCreateException("Errore imprevisto durante la creazione del preventivo.", e);
        }
    }

    private Utente createGuest(PreventivoRequestDto request, String email) {
        Utente guest = new Utente();
        guest.setNome(request.getNome() != null ? request.getNome().trim() : "");
        guest.setCognome(request.getCognome() != null ? request.getCognome().trim() : "");
        guest.setEmail(email);
        guest.setTelefono(request.getTelefono() != null ? normalizzaTelefono(request.getTelefono()) : ""); // <-- Assicurati che non sia nullo
        guest.setIndirizzo(request.getIndirizzo());
        guest.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));
        guest.setAttivo(false);
        guest.setGuest(true);
        guest.setRuolo(Ruolo.UTENTE);
        return utenteRepository.save(guest);
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
    public Page<PreventivoDTO> readByUtenteEmail(String email, Pageable pageable) {
        Page<Preventivo> pagePreventivi = preventivoRepository.findByUtenteEmailIgnoreCase(email, pageable);
        return pagePreventivi.map(preventivoMapper::toDto);
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
            
            // Creazione automatica della prenotazione collegata
            prenotazioneService.createFromPreventivo(id);
            
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
    @Transactional(readOnly = true)
    public Page<PreventivoDTO> readAll(Pageable pageRequest) {
        Page<Preventivo> pagePreventivi = preventivoRepository.findAll(pageRequest);
        return pagePreventivi.map(preventivoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public String readLatestIndirizzoByUtenteEmail(String email) {
        return preventivoRepository.findFirstByUtenteEmailIgnoreCaseOrderByDataEmissioneDesc(email)
                .map(preventivo -> preventivo.getIndirizzo())
                .orElse("");
    }

    private String normalizzaTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            return null;
        }
        String valore = telefono.trim().replaceAll("\\s+", " ");
        if (valore.startsWith("0039")) {
            valore = "+39" + valore.substring(4).trim();
        }

        return valore.startsWith("+39") ? valore : "+39 " + valore;
    }
}