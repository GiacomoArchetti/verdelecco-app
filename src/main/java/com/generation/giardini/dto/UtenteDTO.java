package com.generation.giardini.dto;

import com.generation.giardini.entity.utente.Ruolo;

public record UtenteDTO(
    Long idUtente,
    String nome,
    String cognome,
    String email,
    String telefono,
    Boolean attivo,
    Ruolo ruolo
) {

}
