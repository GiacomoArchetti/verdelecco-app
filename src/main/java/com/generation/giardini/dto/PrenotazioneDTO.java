package com.generation.giardini.dto;

import java.time.LocalDateTime;

public record PrenotazioneDTO(
    Long idPrenotazione,
    Long idPreventivo,
    LocalDateTime dataIntervento,
    String indirizzo,
    String stato
) {

}
