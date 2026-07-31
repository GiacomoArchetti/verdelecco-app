package com.generation.giardini.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.generation.giardini.entity.preventivo.StatoPreventivo;

public record PreventivoDTO(
    Long idPreventivo,
    Long idUtente,
    String nomeUtente,
    String cognomeUtente,
    String indirizzo,
    BigDecimal superficieMq,
    BigDecimal costoStimato,
    String descrizione,
    LocalDateTime dataIntervento,
    LocalDate dataEmissione,
    LocalDate dataScadenza,
    StatoPreventivo stato,
    List<DettaglioPreventivoDTO> dettagli
) {

}
