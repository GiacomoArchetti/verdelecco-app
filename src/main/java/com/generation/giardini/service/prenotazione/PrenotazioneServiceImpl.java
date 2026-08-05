package com.generation.giardini.service.prenotazione;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.PrenotazioneDTO;
import com.generation.giardini.entity.prenotazione.Prenotazione;
import com.generation.giardini.entity.prenotazione.StatoPrenotazione;
import com.generation.giardini.exception.prenotazione.PrenotazioneCreateException;
import com.generation.giardini.exception.prenotazione.PrenotazioneNotFoundException;
import com.generation.giardini.mapper.PrenotazioneMapper;
import com.generation.giardini.repository.PrenotazioneRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService {

    private final PrenotazioneMapper mapper;
    private final PrenotazioneRepository repository;

    @Override
    public boolean create(PrenotazioneDTO dto) {
        if (dto == null) {
            throw new PrenotazioneCreateException("Impossibile creare la prenotazione: il DTO fornito è nullo.");
        }

        try {
            Prenotazione entity = mapper.toEntity(dto);
            repository.save(entity);
            return true;
            
        } catch (Exception e) {
            throw new PrenotazioneCreateException("Errore imprevisto durante la creazione della prenotazione.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrenotazioneDTO> readAll() {
        List<PrenotazioneDTO> lista = new ArrayList<>();
        for (Prenotazione e : repository.findAll()) {
            lista.add(mapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public PrenotazioneDTO readById(Long id) {
        Prenotazione entity = repository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException(id));
                
        return mapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Prenotazione entity = repository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException(id));

        entity.setStato(StatoPrenotazione.ANNULLATA); // Gestione dello stato in base all'enum presente nel DB (ANNULLATA)
        repository.save(entity);

        return true;
    }
}