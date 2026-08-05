package com.generation.giardini.exception.recensione;

public class RecensioneNotFoundException extends RuntimeException {

    /**
     * Costruttore con messaggio predefinito per il fallimento della ricerca della recensione.
     */
    public RecensioneNotFoundException() {
        super("Errore imprevisto durante la ricerca della recensione.");
    }

    /**
     * Costruttore con messaggio personalizzato.
     * 
     * @param message il dettaglio dell'errore
     */
    public RecensioneNotFoundException(String message) {
        super(message);
    }

    /**
     * Costruttore con messaggio e causa scatenante.
     * 
     * @param message il dettaglio dell'errore
     * @param cause   la causa principale dell'eccezione
     */
    public RecensioneNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Costruttore utile per specificare l'id della recensione non trovata.
     * 
     * @param id l'id della recensione cercata
     */
    public RecensioneNotFoundException(Long id) {
        super("Impossibile trovare la recensione con id: '" + id + "'.");
    }

    /**
     * Costruttore utile per specificare l'id della recensione e la causa dell'errore.
     * 
     * @param id    l'id della recensione che ha generato l'errore
     * @param cause la causa principale dell'eccezione
     */
    public RecensioneNotFoundException(Long id, Throwable cause) {
        super("Impossibile trovare la recensione con id: '" + id + "'.", cause);
    }
}