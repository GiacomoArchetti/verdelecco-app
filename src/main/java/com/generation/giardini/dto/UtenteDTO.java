package com.generation.giardini.dto;

public record UtenteDTO(
    Long idUtente,
    String nome,
    String cognome,
    String email,
    String telefono,
    Boolean attivo,
    String ruolo
) {

}
