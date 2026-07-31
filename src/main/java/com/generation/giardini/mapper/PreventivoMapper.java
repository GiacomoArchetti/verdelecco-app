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
        Preventivo preventivo = new Preventivo();
        preventivo.setIdPreventivo(dto.idPreventivo());
        preventivo.setIndirizzo(dto.indirizzo());
        preventivo.setSuperficieMq(dto.superficieMq());
        preventivo.setCostoStimato(dto.costoStimato());
        preventivo.setDescrizione(dto.descrizione());
        preventivo.setDataIntervento(dto.dataIntervento());
        preventivo.setDataEmissione(dto.dataEmissione());
        preventivo.setDataScadenza(dto.dataScadenza());
        if(dto.stato() != null) {
            preventivo.setStatoPreventivo(dto.stato());
        }

        return preventivo;
    }

}
