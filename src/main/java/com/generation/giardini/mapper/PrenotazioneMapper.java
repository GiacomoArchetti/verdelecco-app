package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.PrenotazioneDTO;
import com.generation.giardini.entity.prenotazione.Prenotazione;


@Component
public class PrenotazioneMapper {
    
    public PrenotazioneDTO toDto(Prenotazione entity){

        if(entity == null){
            return null;
        }

        return new PrenotazioneDTO(
            entity.getIdPrenotazione(), 
            entity.getPreventivo() != null ? entity.getPreventivo().getIdPreventivo() : null,
            entity.getDataIntervento(),
            entity.getIndirizzo(),
            entity.getStato());
    }

    public Prenotazione toEntity(PrenotazioneDTO dto){

        if(dto == null){
            return null;
        }

        Prenotazione entity = new Prenotazione();

        entity.setIdPrenotazione(dto.idPrenotazione());
        entity.setDataIntervento(dto.dataIntervento());
        entity.setIndirizzo(dto.indirizzo());
        entity.setStato(dto.stato());

        return entity;
    }

}
