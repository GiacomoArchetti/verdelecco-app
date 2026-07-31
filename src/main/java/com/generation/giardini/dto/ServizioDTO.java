package com.generation.giardini.dto;

import java.math.BigDecimal;

import com.generation.giardini.entity.servizio.NomeServizio;

public record ServizioDTO(
    Long idServizio,
    NomeServizio nome,
    BigDecimal prezzoAlMq,
    Integer minutiAlMq,
    String descrizione,
    Boolean attivo
) {

}
