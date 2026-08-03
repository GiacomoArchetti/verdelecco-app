package com.generation.giardini.mapper;

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.entity.Recensione;

public class RecensioneMapper {

    public RecensioneDTO toDto(Recensione entity){

        if(entity == null){
            return null;
        }

        return new RecensioneDTO(
            entity.getIdRecensione(),
            entity.getPrenotazione() != null ? entity.getPrenotazione().getIdPrenotazione() : null,
            entity.getVoto(),
            entity.getCommento(),
            entity.getDataRecensione()
        );
    }

    public Recensione toEntity(RecensioneDTO dto){

        if(dto == null){
            return null;
        }

        Recensione entity = new Recensione();

        entity.setIdRecensione(dto.idRecensione());
        entity.setVoto(dto.voto());
        entity.setCommento(dto.commento());
        entity.setDataRecensione(dto.dataRecensione());

        return entity;
    }

}
