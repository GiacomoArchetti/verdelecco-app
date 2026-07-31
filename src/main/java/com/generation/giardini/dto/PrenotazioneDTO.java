package com.generation.giardini.dto;

import java.time.LocalDateTime;

import com.generation.giardini.entity.prenotazione.StatoPrenotazione;

public record PrenotazioneDTO(
    Long idPrenotazione,
    Long idPreventivo,
    LocalDateTime dataIntervento,
    String indirizzo,
    StatoPrenotazione stato
) {

}
