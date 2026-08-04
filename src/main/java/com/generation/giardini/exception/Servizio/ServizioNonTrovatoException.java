package com.generation.giardini.exception.Servizio;

public class ServizioNonTrovatoException extends RuntimeException {

    /**
     * Costruttore con messaggio predefinito per il fallimento della creazione del servizio di giardinaggio.
     */
    public ServizioNonTrovatoException() {
        super("Errore imprevisto durante la ricerca del servizio.");
    }

    /**
     * Costruttore con messaggio personalizzato.
     * 
     * @param message il dettaglio dell'errore
     */
    public ServizioNonTrovatoException(String message) {
        super(message);
    }

    /**
     * Costruttore utile per specificare l'id del servizio non trovato.
     * 
     * @param id l'id del servizio cercato
     */
    public ServizioNonTrovatoException(Long id) {
        super("Impossibile trovare il servizio con id: '" + id + "'.");
    }

    /**
     * Costruttore utile per specificare quale servizio si stava tentando di creare.
     * 
     * @param id l'id del servizio che ha generato l'errore
     * @param cause la causa principale dell'eccezione (es. errore di persistenza)
     */
    public ServizioNonTrovatoException(Long id, Throwable cause) {
        super("Impossibile trovare il servizio con id: '" + id + "'.", cause);
    }

}
