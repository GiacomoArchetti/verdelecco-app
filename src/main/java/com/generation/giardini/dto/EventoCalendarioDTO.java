package com.generation.giardini.dto;

public record EventoCalendarioDTO(
    String title,
    String start,
    String end,       // Opzionale: utile se gestisci anche l'orario o eventi multiday
    String display,   // es. "background"
    String color      // es. "#e74c3c"
) {
    // Se vuoi un costruttore comodo per impostare valori di default (es. color e display fissi)
    public EventoCalendarioDTO(String start) {
        this("Occupato", start, null, "background", "#e74c3c");
    }
}
