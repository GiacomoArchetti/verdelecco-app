package com.generation.giardini.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public class PreventivoRequestDto {

    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String indirizzo;
    private String servizio;
    private String dimensioni;

    @NotNull(message = "La data dell intervento è obbligatoria.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataIntervento;
    
    private String dettagli;

    public PreventivoRequestDto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getServizio() {
        return servizio;
    }

    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public LocalDate getDataIntervento() {
        return dataIntervento;
    }

    public void setDataIntervento(LocalDate dataIntervento) {
        this.dataIntervento = dataIntervento;
    }

    public String getDettagli() {
        return dettagli;
    }

    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }
}