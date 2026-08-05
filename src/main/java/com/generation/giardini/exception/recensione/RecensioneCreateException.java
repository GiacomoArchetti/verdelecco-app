package com.generation.giardini.exception.recensione;

public class RecensioneCreateException extends RuntimeException {

    public RecensioneCreateException() {
        super("Errore imprevisto durante la creazione della recensione.");
    }

    public RecensioneCreateException(String message) {
        super(message);
    }

    public RecensioneCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}