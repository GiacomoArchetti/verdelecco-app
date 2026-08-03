package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.entity.servizio.Servizio;

@Component
public class ServizioMapper {

    public ServizioDTO toDto(Servizio entity) {
        if(entity == null) {
            return null;
        }
        return new ServizioDTO(
            entity.getIdServizio(),
            entity.getNome(),
            entity.getPrezzoAlMq(),
            entity.getMinutiAlMq(),
            entity.getDescrizione(),
            entity.getAttivo()
        );
    }

    public Servizio toEntity(ServizioDTO dto) {
        if(dto == null) {
            return null;
        }
        Servizio entity = new Servizio();
        entity.setIdServizio(dto.idServizio());
        entity.setNome(dto.nome());
        entity.setPrezzoAlMq(dto.prezzoAlMq());
        entity.setMinutiAlMq(dto.minutiAlMq());
        entity.setDescrizione(dto.descrizione());
        if(dto.attivo() != null) {
            entity.setAttivo(dto.attivo());
        }
        // Controllo per prevenire il fatto che se attivo è null, il valore di default non viene sovrascritto con null

        return entity;
    }

}
