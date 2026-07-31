package com.generation.giardini.mapper;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.DettaglioPreventivoDTO;
import com.generation.giardini.dto.PreventivoDTO;
import com.generation.giardini.entity.preventivo.Preventivo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PreventivoMapper {

    private final DettaglioPreventivoMapper dettaglioMapper;

    public PreventivoDTO toDto(Preventivo entity) {
        if(entity == null) {
            return null;
        }

        List<DettaglioPreventivoDTO> dettaglioDto = entity.getDettagli() != null
            ? entity.getDettagli().stream().map(dettaglioMapper::toDto).toList()
            : Collections.emptyList();

        return new PreventivoDTO(
            entity.getIdPreventivo(),
            entity.getUtente() != null ? entity.getUtente().getIdUtente() : null,
            entity.getUtente() != null ? entity.getUtente().getNome() : null,
            entity.getUtente() != null ? entity.getUtente().getCognome() : null,
            entity.getIndirizzo(),
            entity.getSuperficieMq(),
            entity.getCostoStimato(),
            entity.getDescrizione(),
            entity.getDataIntervento(),
            entity.getDataEmissione(),
            entity.getDataScadenza(),
            entity.getStatoPreventivo(),
            dettaglioDto
        );
    }

    public Preventivo toEntity(PreventivoDTO dto) {
        if(dto == null) return null;
        Preventivo entity = new Preventivo();

        entity.setIdPreventivo(dto.idPreventivo());
        entity.setIndirizzo(dto.indirizzo());
        entity.setSuperficieMq(dto.superficieMq());
        entity.setCostoStimato(dto.costoStimato());
        entity.setDescrizione(dto.descrizione());
        entity.setDataIntervento(dto.dataIntervento());
        entity.setDataEmissione(dto.dataEmissione());
        entity.setDataScadenza(dto.dataScadenza());
        if(dto.stato() != null) {
            entity.setStatoPreventivo(dto.stato());
        }

        return entity;
    }

}
