package com.generation.giardini.exception.Servizio;

public class ServizioCreateException extends RuntimeException {

    /**
     * Costruttore con messaggio predefinito per il fallimento della creazione del servizio di giardinaggio.
     */
    public ServizioCreateException() {
        super("Errore imprevisto durante la creazione del nuovo servizio.");
    }

    /**
     * Costruttore con messaggio personalizzato.
     * 
     * @param message il dettaglio dell'errore
     */
    public ServizioCreateException(String message) {
        super(message);
    }

    /**
     * Costruttore utile per specificare quale servizio si stava tentando di creare.
     * 
     * @param nomeServizio il nome del servizio che ha generato l'errore
     * @param cause        la causa principale dell'eccezione (es. errore di persistenza)
     */
    public ServizioCreateException(String nomeServizio, Throwable cause) {
        super("Impossibile creare il servizio '" + nomeServizio + "'.", cause);
    }
}