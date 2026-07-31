package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.DettaglioPreventivoDTO;
import com.generation.giardini.entity.DettaglioPreventivo;

@Component
public class DettaglioPreventivoMapper {

    public DettaglioPreventivoDTO toDto(DettaglioPreventivo entity) {
        if(entity == null) {
            return null;
        }
        return new DettaglioPreventivoDTO(
            entity.getIdDettaglio(),
            entity.getPreventivo() != null ? entity.getPreventivo().getIdPreventivo() : null,
            entity.getServizio() != null ? entity.getServizio().getIdServizio() : null,
            entity.getServizio() != null && entity.getServizio().getNome() != null ? entity.getServizio().getNome().name() : null,
            entity.getQuantita()
        );
    }

    public DettaglioPreventivo toEntity(DettaglioPreventivoDTO dto) {
        if(dto == null) {
            return null;
        }
        DettaglioPreventivo dettaglio = new DettaglioPreventivo();
        dettaglio.setIdDettaglio(dto.idDettaglio());
        dettaglio.setQuantita(dto.quantita());

        return dettaglio;
    }

}
