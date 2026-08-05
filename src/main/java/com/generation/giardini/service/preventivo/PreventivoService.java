package com.generation.giardini.service.preventivo;

import java.util.List;
import com.generation.giardini.dto.PreventivoDTO;

public interface PreventivoService {

    boolean create(PreventivoDTO dto);

    List<PreventivoDTO> readAll();

    List<PreventivoDTO> readAllByUtente(Long idUtente);

    PreventivoDTO readById(Long id);

    boolean delete(Long id);
}