package com.generation.giardini.exception.prenotazione;

public class PrenotazioneNotFoundException extends RuntimeException {

    /**
     * Costruttore con messaggio predefinito per il fallimento della ricerca della prenotazione.
     */
    public PrenotazioneNotFoundException() {
        super("Errore imprevisto durante la ricerca della prenotazione.");
    }

    /**
     * Costruttore con messaggio personalizzato.
     * 
     * @param message il dettaglio dell'errore
     */
    public PrenotazioneNotFoundException(String message) {
        super(message);
    }

    /**
     * Costruttore con messaggio e causa scatenante.
     * 
     * @param message il dettaglio dell'errore
     * @param cause   la causa principale dell'eccezione
     */
    public PrenotazioneNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Costruttore utile per specificare l'id della prenotazione non trovata.
     * 
     * @param id l'id della prenotazione cercata
     */
    public PrenotazioneNotFoundException(Long id) {
        super("Impossibile trovare la prenotazione con id: '" + id + "'.");
    }

    /**
     * Costruttore utile per specificare l'id della prenotazione e la causa dell'errore.
     * 
     * @param id    l'id della prenotazione che ha generato l'errore
     * @param cause la causa principale dell'eccezione
     */
    public PrenotazioneNotFoundException(Long id, Throwable cause) {
        super("Impossibile trovare la prenotazione con id: '" + id + "'.", cause);
    }
}