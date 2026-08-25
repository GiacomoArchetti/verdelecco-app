package com.generation.giardini.service.utente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.UtenteDTO;
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

}
