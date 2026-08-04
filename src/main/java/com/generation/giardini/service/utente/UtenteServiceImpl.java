package com.generation.giardini.service.utente;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.UtenteDTO;
import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.exception.utente.UtenteCreateException;
import com.generation.giardini.mapper.UtenteMapper;
import com.generation.giardini.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteMapper mapper;
    private final UtenteRepository repository;

    @Override
    public boolean create(UtenteDTO dto) {
    // 1. Controllo preliminare sul DTO
        if (dto == null) {
            throw new UtenteCreateException("Impossibile creare l'utente: il DTO fornito è nullo.");
        }

        try {
            // 2. Conversione e salvataggio
            Utente entity = mapper.toEntity(dto);
            repository.save(entity);
            
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
    public List<UtenteDTO> readAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    @Override
    public List<UtenteDTO> readAllActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAllActive'");
    }

    @Override
    public List<UtenteDTO> readAllNotActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAllNotActive'");
    }

    @Override
    public UtenteDTO readById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readById'");
    }

    @Override
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
