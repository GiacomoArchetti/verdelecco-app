package com.generation.giardini.service.recensione;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.entity.Recensione;
import com.generation.giardini.exception.recensione.RecensioneCreateException;
import com.generation.giardini.exception.recensione.RecensioneNotFoundException;
import com.generation.giardini.mapper.RecensioneMapper;
import com.generation.giardini.repository.RecensioneRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService {

    private final RecensioneMapper mapper;
    private final RecensioneRepository repository;

    @Override
    public boolean create(RecensioneDTO dto) {
        if (dto == null) {
            throw new RecensioneCreateException("Impossibile creare la recensione: il DTO fornito è nullo.");
        }

        try {
            Recensione entity = mapper.toEntity(dto);
            repository.save(entity);
            return true;
            
        } catch (Exception e) {
            throw new RecensioneCreateException("Errore imprevisto durante la creazione della recensione.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecensioneDTO> readAll() {
        List<RecensioneDTO> lista = new ArrayList<>();
        for (Recensione e : repository.findAll()) {
            lista.add(mapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public RecensioneDTO readById(Long id) {
        Recensione entity = repository.findById(id)
                .orElseThrow(() -> new RecensioneNotFoundException(id));
                
        return mapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Recensione entity = repository.findById(id)
                .orElseThrow(() -> new RecensioneNotFoundException(id));

        repository.delete(entity); //Cancello fisicamente la recensione anche dal db perchè non ha valore storico

        return true;
    }
}