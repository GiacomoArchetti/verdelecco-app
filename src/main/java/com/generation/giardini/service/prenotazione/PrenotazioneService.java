package com.generation.giardini.service.prenotazione;

import java.util.List;

import com.generation.giardini.dto.PrenotazioneDTO;

public interface PrenotazioneService {

    boolean create(PrenotazioneDTO dto);

    List<PrenotazioneDTO> readAll();

    PrenotazioneDTO readById(Long id);

    boolean delete(Long id);

}
