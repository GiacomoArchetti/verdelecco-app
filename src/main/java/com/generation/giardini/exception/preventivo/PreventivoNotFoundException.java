package com.generation.giardini.exception.preventivo;

public class PreventivoNotFoundException extends RuntimeException {

    /**
     * Costruttore con messaggio predefinito per il fallimento della ricerca del preventivo.
     */
    public PreventivoNotFoundException() {
        super("Errore imprevisto durante la ricerca del preventivo.");
    }

    /**
     * Costruttore con messaggio personalizzato.
     * 
     * @param message il dettaglio dell'errore
     */
    public PreventivoNotFoundException(String message) {
        super(message);
    }

    /**
     * Costruttore con messaggio e causa scatenante.
     * 
     * @param message il dettaglio dell'errore
     * @param cause   la causa principale dell'eccezione
     */
    public PreventivoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Costruttore utile per specificare l'id del preventivo non trovato.
     * 
     * @param id l'id del preventivo cercato
     */
    public PreventivoNotFoundException(Long id) {
        super("Impossibile trovare il preventivo con id: '" + id + "'.");
    }

    /**
     * Costruttore utile per specificare l'id del preventivo e la causa dell'errore.
     * 
     * @param id    l'id del preventivo che ha generato l'errore
     * @param cause la causa principale dell'eccezione
     */
    public PreventivoNotFoundException(Long id, Throwable cause) {
        super("Impossibile trovare il preventivo con id: '" + id + "'.", cause);
    }
}