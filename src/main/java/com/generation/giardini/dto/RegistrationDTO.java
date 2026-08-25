package com.generation.giardini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationDTO(
    @NotBlank(message = "Il nome è obbligatorio")
    String nome,

    @NotBlank(message = "Il cognome è obbligatorio")
    String cognome,

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    String email,

    String telefono,

    @NotBlank(message = "La password è obbligatoria")
    @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
    String password,

    @NotBlank(message = "La conferma password è obbligatoria")
    String passwordConfirm
) {
    
}