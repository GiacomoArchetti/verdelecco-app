package com.generation.giardini.exception.preventivo;

public class PreventivoCreateException extends RuntimeException {

    /*
    Costruttore con messaggio predefinito per il fallimento della creazione del preventivo.*/
    public PreventivoCreateException() {
        super("Errore imprevisto durante la creazione del preventivo.");
    }

    /**
    Costruttore con messaggio personalizzato.
    @param message il dettaglio dell'errore
    */

    public PreventivoCreateException(String message) {
        super(message);
    }


    /**
    Costruttore utile per specificare quale preventivo si stava tentando di creare.
    @param preventivo   il preventivo che ha generato l'errore
    @param cause        la causa principale dell'eccezione (es. errore di persistenza)*/
    public PreventivoCreateException(String preventivo, Throwable cause) {
        super("Impossibile creare il utente '" + preventivo + "'.", cause);
    }
}
