package com.generation.giardini.service.preventivo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.PreventivoDTO;
import com.generation.giardini.entity.preventivo.Preventivo;
import com.generation.giardini.entity.preventivo.StatoPreventivo;
import com.generation.giardini.exception.preventivo.PreventivoCreateException;
import com.generation.giardini.exception.preventivo.PreventivoNotFoundException;
import com.generation.giardini.mapper.PreventivoMapper;
import com.generation.giardini.repository.PreventivoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PreventivoServiceImpl implements PreventivoService {

    private final PreventivoMapper mapper;
    private final PreventivoRepository repository;

    @Override
    public boolean create(PreventivoDTO dto) {
        if (dto == null) {
            throw new PreventivoCreateException("Impossibile creare il preventivo: il DTO fornito è nullo.");
        }

        try {
            Preventivo entity = mapper.toEntity(dto);
            repository.save(entity);
            return true;
            
        } catch (Exception e) {
            throw new PreventivoCreateException("Errore imprevisto durante la creazione del preventivo.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreventivoDTO> readAll() {
        List<PreventivoDTO> lista = new ArrayList<>();
        for (Preventivo e : repository.findAll()) {
            lista.add(mapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreventivoDTO> readAllByUtente(Long idUtente) {
        List<PreventivoDTO> lista = new ArrayList<>();
        for (Preventivo e : repository.findAll()) {
            // Filtro basato sull'utente associato al preventivo
            if (e.getUtente() != null && e.getUtente().getIdUtente().equals(idUtente)) {
                lista.add(mapper.toDto(e));
            }
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public PreventivoDTO readById(Long id) {
        Preventivo entity = repository.findById(id)
                .orElseThrow(() -> new PreventivoNotFoundException(id));
                
        return mapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Preventivo entity = repository.findById(id)
                .orElseThrow(() -> new PreventivoNotFoundException(id));
                
        entity.setStatoPreventivo(StatoPreventivo.ANNULLATO); //Setto lo stato su ANNULLATO per un preventivo cancellato/eliminato
        repository.save(entity);

        return true;
    }
}