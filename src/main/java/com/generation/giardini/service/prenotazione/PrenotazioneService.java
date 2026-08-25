package com.generation.giardini.service.prenotazione;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.generation.giardini.dto.EventoCalendarioDTO;
import com.generation.giardini.dto.PrenotazioneDTO;

/**
 * <h3>PrenotazioneService</h3>
 * <p>Questo service serve per la gestione completa delle prenotazioni degli interventi,
 *    consentendo la creazione diretta, la generazione automatica da preventivo accettato,
 *    la consultazione (anche paginata) e il recupero degli eventi per il calendario.</p>
 */
public interface PrenotazioneService {

    // Serve per registrare una nuova prenotazione -> true -> Msg = prenotazione creata con successo, false -> exception e Msg = errore
    boolean create(PrenotazioneDTO dto);

    // Genera automaticamente una prenotazione a partire da un preventivo accettato -> true -> success, false -> exception e Msg = errore
    boolean createFromPreventivo(Long preventivoId);

    // READ / FIND ALL -> Restituisce tutte le prenotazioni registrate
    List<PrenotazioneDTO> readAll();

    // READ / FIND ALL -> Restituisce una pagina di prenotazioni registrate in base ai parametri di paginazione
    Page<PrenotazioneDTO> readAll(Pageable pageRequest);

    // READ BY UTENTE EMAIL -> Restituisce una pagina di prenotazioni associate all'email dell'utente
    Page<PrenotazioneDTO> readByUtenteEmail(String email, Pageable pageable);

    // FIND BY ID -> Restituisce il dettaglio della prenotazione tramite il suo ID
    PrenotazioneDTO readById(Long id);

    // DELETE -> true -> msg = prenotazione eliminata, false -> exception e Msg = errore
    boolean delete(Long id);

    // CALENDARIO -> Restituisce la lista degli eventi prenotati in un determinato intervallo di date
    List<EventoCalendarioDTO> getEventiCalendario(LocalDate start, LocalDate end);

}