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
        Servizio servizio = new Servizio();
        servizio.setIdServizio(dto.idServizio());
        servizio.setNome(dto.nome());
        servizio.setPrezzoAlMq(dto.prezzoAlMq());
        servizio.setMinutiAlMq(dto.minutiAlMq());
        servizio.setDescrizione(dto.descrizione());
        if(dto.attivo() != null) {
            servizio.setAttivo(dto.attivo());
        }
        // Controllo per prevenire il fatto che se attivo o ruolo sono null, il valore di default non viene sovrascritto con null

        return servizio;
    }

}
