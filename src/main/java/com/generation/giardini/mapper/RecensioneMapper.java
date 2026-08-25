package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.entity.Recensione;

@Component
public class RecensioneMapper {

    public RecensioneDTO toDto(Recensione entity){

        if(entity == null){
            return null;
        }

        // Estrazione sicura dell'email risalendo le relazioni
        String email = null;
        if (entity.getPrenotazione() != null 
                && entity.getPrenotazione().getPreventivo() != null 
                && entity.getPrenotazione().getPreventivo().getUtente() != null) {
            email = entity.getPrenotazione().getPreventivo().getUtente().getEmail();
        }

        return new RecensioneDTO(
            entity.getIdRecensione(),
            entity.getPrenotazione() != null ? entity.getPrenotazione().getIdPrenotazione() : null,
            email,
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
