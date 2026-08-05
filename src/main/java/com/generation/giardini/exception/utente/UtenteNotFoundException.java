package com.generation.giardini.exception.utente;

public class UtenteNotFoundException extends RuntimeException{

    /**
    Costruttore con messaggio predefinito per il fallimento della ricerca dell'utente.*/
    public UtenteNotFoundException() {
    super("Errore imprevisto durante la ricerca dell'utente.");
    }

    /**
    Costruttore con messaggio personalizzato.
    @param message il dettaglio dell'errore*/
    public UtenteNotFoundException(String message) {
    super(message);}

    /**
    Costruttore utile per specificare l'id dell'utente non trovato.
    @param id l'id dell'utente cercato*/
    public UtenteNotFoundException(Long id) {
    super("Impossibile trovare l'utente con id: '" + id + "'.");}

    /**
    Costruttore utile per specificare quale utente si stava tentando di creare.
    @param id l'id dell'utente che ha generato l'errore
    @param cause la causa principale dell'eccezione (es. errore di persistenza)*/
    public UtenteNotFoundException(Long id, Throwable cause) {
    super("Impossibile trovare l'utente con id: '" + id + "'.", cause);
    }
}
