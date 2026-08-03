package com.generation.giardini.dto;

import java.time.LocalDateTime;

public record RecensioneDTO(
    Long idRecensione,
    Long idPrenotazione,
    Byte voto,  // Consigliato di mettere Byte anche qui per evitare inutili conversioni o problemi di validazione
    String commento,
    LocalDateTime dataRecensione
) {

}
