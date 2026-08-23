package com.generation.giardini.service.prenotazione;

import java.time.LocalDate;
import java.util.List;

import com.generation.giardini.dto.EventoCalendarioDTO;
import com.generation.giardini.dto.PrenotazioneDTO;

public interface PrenotazioneService {

    boolean create(PrenotazioneDTO dto);

    boolean createFromPreventivo(Long preventivoId);

    List<PrenotazioneDTO> readAll();

    PrenotazioneDTO readById(Long id);

    boolean delete(Long id);

    List<EventoCalendarioDTO> getEventiCalendario(LocalDate start, LocalDate end);

}
