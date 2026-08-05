package com.generation.giardini.service.recensione;

import java.util.List;
import com.generation.giardini.dto.RecensioneDTO;

public interface RecensioneService {

    boolean create(RecensioneDTO dto);

    List<RecensioneDTO> readAll();

    RecensioneDTO readById(Long id);

    boolean delete(Long id);
}