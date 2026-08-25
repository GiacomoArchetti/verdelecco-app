package com.generation.giardini.service.recensione;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.generation.giardini.dto.RecensioneDTO;

/**
 * <h3>RecensioneService</h3>
 * <p>Questo service serve per la gestione delle recensioni dei clienti,
 *    consentendo la creazione, la consultazione (anche paginata) e l'eliminazione dei feedback.</p>
 */
public interface RecensioneService {

    // Serve per registrare una nuova recensione generica -> true -> Msg = recensione creata con successo, false -> exception e Msg = errore
    boolean create(RecensioneDTO dto);

    // Serve per creare una recensione associata a una specifica prenotazione conclusa -> true -> success, false -> exception e Msg = errore
    boolean createForPrenotazione(Long idPrenotazione, String email, Byte voto, String commento);

    // READ / FIND ALL -> Restituisce tutte le recensioni registrate
    List<RecensioneDTO> readAll();

    // READ / FIND ALL -> Restituisce una pagina di recensioni registrate in base ai parametri di paginazione
    Page<RecensioneDTO> readAll(Pageable pageRequest);

    // FIND BY ID -> Restituisce il dettaglio della recensione tramite il suo ID
    RecensioneDTO readById(Long id);

    // DELETE -> true -> msg = recensione eliminata, false -> exception e Msg = errore
    boolean delete(Long id);
}