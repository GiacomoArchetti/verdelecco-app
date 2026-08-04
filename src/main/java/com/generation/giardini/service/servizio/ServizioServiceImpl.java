package com.generation.giardini.service.servizio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.entity.servizio.Servizio;
import com.generation.giardini.exception.Servizio.ServizioCreateException;
import com.generation.giardini.exception.Servizio.ServizioNonTrovatoException;
import com.generation.giardini.mapper.ServizioMapper;
import com.generation.giardini.repository.ServizioRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServizioServiceImpl implements ServizioService{

    private final ServizioMapper mapper;
    private final ServizioRepository repository;

    @Override
    public boolean create(ServizioDTO dto) {
        // 1. Controllo preliminare sul DTO
        if (dto == null) {
            throw new ServizioCreateException("Impossibile creare il servizio: il DTO fornito è nullo.");
        }

        try {
            // 2. Conversione e salvataggio
            Servizio entity = mapper.toEntity(dto);
            repository.save(entity);
            
            // Se arriviamo qui, il salvataggio è andato a buon fine
            return true;
            
        } catch (Exception e) {
            // 3. Intercettiamo qualsiasi errore di salvataggio e lanciamo la nostra eccezione personalizzata
            // Usiamo il nome del servizio (o una stringa di fallback) per dare un messaggio chiaro
            String nomeServizio = dto.nome() != null ? dto.nome() : "Senza nome";
            throw new ServizioCreateException(nomeServizio, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServizioDTO> readAll() {
    List<ServizioDTO> lista = new ArrayList<>();
        for(Servizio s : repository.findAll()){
            lista.add(mapper.toDto(s));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServizioDTO> readAllActive() {
        List<ServizioDTO> lista = new ArrayList<>();
        for(Servizio s : repository.findAll()){
            if(s.getAttivo() == true){
                lista.add(mapper.toDto(s));
            }
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServizioDTO> readAllNotActive() {
        List<ServizioDTO> lista = new ArrayList<>();
        for(Servizio s : repository.findAll()){
            if(s.getAttivo() == false){
                lista.add(mapper.toDto(s));
            }
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public ServizioDTO readById(Long id) {
        Servizio servizio = repository.findById(id)
                                        .orElseThrow(() -> new ServizioNonTrovatoException(id)); //Se non trova lancia eccezione custom
                
        return mapper.toDto(servizio);
    }

    @Override
    public boolean delete(Long id) {
        Servizio servizio = repository.findById(id)
                                        .orElseThrow(() -> new ServizioNonTrovatoException(id));

        repository.delete(servizio);

        return true;
    }

}