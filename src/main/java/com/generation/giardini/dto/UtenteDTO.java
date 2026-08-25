package com.generation.giardini.dto;

public record UtenteDTO(
    Long idUtente,
    String nome,
    String cognome,
    String email,
    String telefono,
    String indirizzo,
    Boolean attivo,
    Boolean guest,
    String ruolo
) {

}