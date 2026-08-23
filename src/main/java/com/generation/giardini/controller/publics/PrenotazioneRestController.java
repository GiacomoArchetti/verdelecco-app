package com.generation.giardini.controller.publics;

import com.generation.giardini.dto.EventoCalendarioDTO;
import com.generation.giardini.service.prenotazione.PrenotazioneService;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneRestController {

    private final PrenotazioneService prenotazioneService;


    @GetMapping("/occupate")
    public ResponseEntity<List<EventoCalendarioDTO>> getGiorniOccupati(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        
        List<EventoCalendarioDTO> eventi = prenotazioneService.getEventiCalendario(start, end);
        return ResponseEntity.ok(eventi);
    }
}
