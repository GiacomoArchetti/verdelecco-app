package com.generation.giardini.service.preventivo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.generation.giardini.dto.PreventivoDTO;
import com.generation.giardini.dto.PreventivoRequestDto;

/**
 * <h3>PreventivoService</h3>
 * <p>Questo service serve per la gestione completa dei preventivi, consentendo la creazione,
 *    la consultazione (anche paginata) e la gestione dello stato (accettazione/rifiuto) da parte di utenti e guest.</p>
 */
public interface PreventivoService {

    // Serve per creare un nuovo preventivo per un utente registrato -> true -> Msg = preventivo creato con successo, false -> exception e Msg = errore
    boolean create(PreventivoDTO dto);

    // Serve per richiedere un preventivo come utente non autenticato (guest) -> true -> Msg = richiesta inviata, false -> exception e Msg = errore
    boolean createGuestRequest(PreventivoRequestDto request);

    // READ / FIND ALL -> Restituisce tutti i preventivi registrati
    List<PreventivoDTO> readAll();

    // READ / FIND ALL -> Restituisce una pagina di preventivi registrati in base ai parametri di paginazione
    Page<PreventivoDTO> readAll(Pageable pageRequest);

    // READ BY UTENTE -> Restituisce la lista dei preventivi associati a uno specifico utente
    List<PreventivoDTO> readAllByUtente(Long idUtente);

    // FIND BY ID -> Restituisce il dettaglio del preventivo tramite il suo ID
    PreventivoDTO readById(Long id);

    // DELETE -> true -> msg = preventivo eliminato, false -> exception e Msg = errore
    boolean delete(Long id);

    // ACCEPT -> Accetta il preventivo cambiando il suo stato -> true -> success, false -> exception e Msg = errore
    boolean accept(Long id);

    // REJECT -> Rifiuta il preventivo cambiando il suo stato -> true -> success, false -> exception e Msg = errore
    boolean reject(Long id);
}