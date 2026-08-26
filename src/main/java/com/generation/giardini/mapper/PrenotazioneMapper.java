package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.PrenotazioneDTO;
import com.generation.giardini.entity.prenotazione.Prenotazione;
import com.generation.giardini.entity.prenotazione.StatoPrenotazione;


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
            entity.getStato() != null ? entity.getStato().name() : null,
            // MODIFICA: valorizziamo il campo recensita (se l'entità ha la relazione o controllando la presenza)
            false // Valore impostato di default
        );
    }

    public Prenotazione toEntity(PrenotazioneDTO dto){

        if(dto == null){
            return null;
        }

        Prenotazione entity = new Prenotazione();

        entity.setIdPrenotazione(dto.idPrenotazione());
        entity.setDataIntervento(dto.dataIntervento());
        entity.setIndirizzo(dto.indirizzo());

        if (dto.stato() == null) {
            entity.setStato(StatoPrenotazione.PROGRAMMATA);
        } else {
            entity.setStato(switch (dto.stato().toUpperCase()) {
                case "COMPLETATA" -> StatoPrenotazione.COMPLETATA;
                case "ANNULLATA" -> StatoPrenotazione.ANNULLATA;
                default -> StatoPrenotazione.PROGRAMMATA;
            }); //Sintassi switch da java 14+
        }

        // NOTA: Nessuna modifica necessaria qui in toEntity poichè 'recensita' è un flag derivato/di sola lettura per la vista.

        return entity;
    }

}