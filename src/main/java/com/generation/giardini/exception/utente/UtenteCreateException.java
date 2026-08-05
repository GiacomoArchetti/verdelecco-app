package com.generation.giardini.exception.utente;

public class UtenteCreateException extends RuntimeException {

    /*
    Costruttore con messaggio predefinito per il fallimento della creazione dell'utente.*/
    public UtenteCreateException() {
        super("Errore imprevisto durante la creazione del nuovo utente.");
    }

    /**
    Costruttore con messaggio personalizzato.
    @param message il dettaglio dell'errore
    */

    public UtenteCreateException(String message) {
        super(message);
    }


    /**
    Costruttore utile per specificare quale utente si stava tentando di creare.
    @param nomeUtente   il nome dell'utente che ha generato l'errore
    @param cause        la causa principale dell'eccezione (es. errore di persistenza)*/
    public UtenteCreateException(String nomeUtente, Throwable cause) {
        super("Impossibile creare il utente '" + nomeUtente + "'.", cause);
    }
}
