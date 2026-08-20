package com.generation.giardini.service.recensione;

import java.util.List;
import com.generation.giardini.dto.RecensioneDTO;

public interface RecensioneService {

    boolean create(RecensioneDTO dto);

    boolean createForPrenotazione(Long idPrenotazione, String email, Byte voto, String commento);

    List<RecensioneDTO> readAll();

    RecensioneDTO readById(Long id);

    boolean delete(Long id);
}