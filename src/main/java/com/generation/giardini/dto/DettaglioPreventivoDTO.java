package com.generation.giardini.dto;

public record DettaglioPreventivoDTO(
    Long idDettaglio,
    Long idPreventivo,
    Long idServizio,
    String nomeServizio,
    Integer quantita
) {

}
