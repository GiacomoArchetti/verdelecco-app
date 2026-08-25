package com.generation.giardini.service.servizio;

import java.util.List;
import java.util.Map;

import com.generation.giardini.dto.ServizioDTO;

/**
 * <h3>ServizioService</h3>
 * <p>Questo Service serve all'admin per poter controllare, creare o gestire i propri servizi direttamente dal portale Admin</p>
 */
public interface ServizioService {

    //CREATE / UPDATE -> L'admin potrebbe voler creare un servizio -> true -> Msg = Servizio creato, false -> exception e Msg = errore
    boolean create(ServizioDTO dto);
    
    //READ / FIND ALL -> Restituisce tutti i servizi presenti
    List<ServizioDTO> readAll();

    //READ ALL ACTIVE -> Restituisce tutti i servizi presenti attivi
    List<ServizioDTO> readAllActive();

    // READ ALL ATTIVI PER SELECT -> Restituisce la lista di opzioni (label/value) dei soli servizi attivi per le select della UI
    List<Map<String, String>> readAllAttiviOptions();

    //READ ALL NOT ACTIVE -> Restituisce tutti i servizi presenti NON attivi
    List<ServizioDTO> readAllNotActive();

    //FIND BY ID
    ServizioDTO readById(Long id);

    //DELETE -> true -> msg = servizio eliminato, false -> exception e Msg = errore, servizio non creato
    boolean delete(Long id);

}