package com.generation.giardini.service.utente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.dto.RegistrationDTO;
import com.generation.giardini.dto.UtenteDTO;
import com.generation.giardini.entity.utente.Ruolo;
import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.exception.utente.UtenteCreateException;
import com.generation.giardini.exception.utente.UtenteNotFoundException;
import com.generation.giardini.mapper.UtenteMapper;
import com.generation.giardini.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteMapper utenteMapper;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean create(UtenteDTO dto) {
    // 1. Controllo preliminare sul DTO
        if (dto == null) {
            throw new UtenteCreateException("Impossibile creare l'utente: il DTO fornito è nullo.");
        }

        try {
            // 2. Conversione e salvataggio
            Utente entity = utenteMapper.toEntity(dto);
            utenteRepository.save(entity);
            
            // Se arriviamo qui, il salvataggio è andato a buon fine
            return true;
            
        } catch (Exception e) {
            // 3. Intercettiamo qualsiasi errore di salvataggio e lanciamo la nostra eccezione personalizzata
            // Usiamo il nome del utente (o una stringa di fallback) per dare un messaggio chiaro
            String nomeUtente = dto.nome() != null ? dto.nome() : "Senza nome";
            throw new UtenteCreateException(nomeUtente, e);
        }
    }

    @Override
    public boolean register(RegistrationDTO dto) {
        if (dto == null) {
            throw new UtenteCreateException(
                    "Impossibile registrare l'utente: il DTO fornito è nullo.");
        }

        String emailClean = dto.email() != null
                ? dto.email().trim().toLowerCase()
                : "";

        if (emailClean.isEmpty()) {
            throw new UtenteCreateException("L'email è obbligatoria.");
        }

        try {
            Optional<Utente> optionalUtente =
                    utenteRepository.findByEmailIgnoreCase(emailClean);

            Utente utente;

            if (optionalUtente.isPresent()) {
                utente = optionalUtente.get();

                // Se l'email è associata a un utente registrato (non guest), blocchiamo la registrazione
                if (!Boolean.TRUE.equals(utente.getGuest())) {
                    throw new UtenteCreateException("Questa email è già registrata.");
                }
                
                // Se è un guest, procediamo a convertire l'entità esistente sovrascrivendo i dati
            } else {
                // Nessun utente esistente: creiamo una nuova istanza
                utente = new Utente();
                utente.setEmail(emailClean);
                utente.setRuolo(Ruolo.UTENTE);
            }

            // Popolamento / Aggiornamento dei campi comuni tramite DTO
            utente.setNome(dto.nome() != null ? dto.nome().trim() : "");
            utente.setCognome(dto.cognome() != null ? dto.cognome().trim() : "");
            
            String telefono = normalizzaTelefono(dto.telefono());
            if (telefono != null) {
                utente.setTelefono(telefono);
            }
            
            utente.setPassword(passwordEncoder.encode(dto.password()));
            utente.setAttivo(true);
            utente.setGuest(false);

            utenteRepository.save(utente);

            return true;

        } catch (UtenteCreateException e) {
            throw e;
        } catch (Exception e) {
            throw new UtenteCreateException(emailClean, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtenteDTO> readAll() {
    List<UtenteDTO> lista = new ArrayList<>();
        for(Utente e : utenteRepository.findAll()){
            lista.add(utenteMapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtenteDTO> readAllActive() {
        List<UtenteDTO> lista = new ArrayList<>();
        for(Utente e : utenteRepository.findAll()){
            if(e.getAttivo() == true){
                lista.add(utenteMapper.toDto(e));
            }
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtenteDTO> readAllNotActive() {
        List<UtenteDTO> lista = new ArrayList<>();
        for(Utente e : utenteRepository.findAll()){
            if(e.getAttivo() == false){
                lista.add(utenteMapper.toDto(e));
            }
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public UtenteDTO readById(Long id) {
        Utente entity = utenteRepository.findById(id)
                                        .orElseThrow(() -> new UtenteNotFoundException(id)); //Se non trova lancia eccezione custom
                
        return utenteMapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Utente entity = utenteRepository.findById(id)
                                        .orElseThrow(() -> new UtenteNotFoundException(id)); //Se non trova lancia eccezione custom

        entity.setAttivo(false);
        utenteRepository.save(entity);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtenteDTO> readAll(Pageable pageRequest) {
        Page<Utente> pageUtenti = utenteRepository.findAll(pageRequest);
        return pageUtenti.map(utenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtenteDTO> readAllClients(Pageable pageRequest) {
        Page<Utente> pageClienti = utenteRepository.findByRuolo(Ruolo.UTENTE, pageRequest);
        return pageClienti.map(utenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UtenteDTO readByEmail(String email) {
        Utente utente = utenteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UtenteNotFoundException("Utente non trovato con email: " + email));
        return utenteMapper.toDto(utente);
    }

    @Override
    public void updateDatiContatto(String email, String telefono, String indirizzo) {
        Utente utente = utenteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UtenteNotFoundException("Utente non trovato con email: " + email));

        String telefonoNormalizzato = normalizzaTelefono(telefono);
        if (telefonoNormalizzato != null) {
            utente.setTelefono(telefonoNormalizzato);
        }
        utente.setIndirizzo(indirizzo == null ? null : indirizzo.trim());
        utenteRepository.save(utente);
    }

    @Override
    @Transactional(readOnly = true)
    public PreventivoRequestDto createPreventivoRequestForUser(String email) {
        PreventivoRequestDto request = new PreventivoRequestDto();
        utenteRepository.findByEmailIgnoreCase(email).ifPresent(utente -> {
            request.setNome((utente.getNome() + " " + utente.getCognome()).trim());
            request.setEmail(utente.getEmail());
            request.setTelefono(telefonoPerForm(utente.getTelefono()));
            request.setIndirizzo(utente.getIndirizzo());
        });
        return request;
    }

    private String telefonoPerForm(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            return telefono;
        }
        return telefono.trim().replaceFirst("^(?:\\+|00)\\d{1,3}[\\s.-]*", "");
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