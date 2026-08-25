package com.generation.giardini.service.utente;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.generation.giardini.dto.UtenteDTO;

/**
 * <h3>UtenteService</h3>
 * <p>Questo service serve per poter visualizzare e gestire gli utenti da parte dell'admin come clienti,
 *    e verrà anche usato per creare/registrare nuovi clienti</p>
 */
public interface UtenteService {

    // Serve per registrare un nuovo cliente -> true -> Msg = cliente registrato con successo, false -> exception e Msg = errore
    boolean create(UtenteDTO dto);

    //READ / FIND ALL -> Restituisce tutti gli utenti registrati
    List<UtenteDTO> readAll();

    // READ / FIND ALL -> Restituisce una pagina di utenti registrati in base ai parametri di paginazione
    Page<UtenteDTO> readAll(Pageable pageRequest);

    //READ ALL ACTIVE -> Restituisce tutti gli utenti registrati attivi
    List<UtenteDTO> readAllActive();

    //READ ALL NOT ACTIVE -> Restituisce tutti gli utenti registrati NON attivi
    List<UtenteDTO> readAllNotActive();

    //FIND BY ID
    UtenteDTO readById(Long id);

    //DELETE -> true -> msg = cliente registrato eliminato, false -> exception e Msg = errore, servizio non creato
    boolean delete(Long id);
}
