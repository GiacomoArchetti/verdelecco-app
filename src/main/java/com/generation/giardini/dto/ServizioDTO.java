package com.generation.giardini.dto;

import java.math.BigDecimal;

public record ServizioDTO(
    Long idServizio,
    String nome,
    BigDecimal prezzoAlMq,
    Integer minutiAlMq,
    String descrizione,
    Boolean attivo
) {

}
