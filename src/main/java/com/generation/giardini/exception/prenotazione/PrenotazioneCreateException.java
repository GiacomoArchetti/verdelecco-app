package com.generation.giardini.exception.prenotazione;

public class PrenotazioneCreateException extends RuntimeException {

    /*
    Costruttore con messaggio predefinito per il fallimento della creazione della prenotazione.*/
    public PrenotazioneCreateException() {
        super("Errore imprevisto durante la creazione della prenotazione.");
    }

    /**
    Costruttore con messaggio personalizzato.
    @param message il dettaglio dell'errore
    */
    public PrenotazioneCreateException(String message) {
        super(message);
    }

    /**
    Costruttore utile per specificare quale prenotazione si stava tentando di creare.
    @param preventivo   il preventivo che ha generato l'errore
    @param cause        la causa principale dell'eccezione (es. errore di persistenza)*/
    public PrenotazioneCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}