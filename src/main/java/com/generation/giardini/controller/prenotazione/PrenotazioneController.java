package com.generation.giardini.controller.prenotazione;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.giardini.dto.PrenotazioneDTO;
import com.generation.giardini.service.prenotazione.PrenotazioneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    // CREATE: POST /api/prenotazioni
    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody PrenotazioneDTO dto) {
        boolean risultato = prenotazioneService.create(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(risultato); // 201 CREATED O -> GlobalExceptionHandler -> PrenotazioneCreateException -> 400 BAD REQUEST
    }

    // READ ALL: GET /api/prenotazioni
    @GetMapping
    public ResponseEntity<List<PrenotazioneDTO>> readAll() {
        List<PrenotazioneDTO> prenotazioni = prenotazioneService.readAll();
        
        return ResponseEntity.ok(prenotazioni); // 200 OK con la lista (anche vuota)
    }

    // READ BY ID: GET /api/prenotazioni/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PrenotazioneDTO> readById(@PathVariable Long id) {
        PrenotazioneDTO prenotazione = prenotazioneService.readById(id);
        
        return ResponseEntity.ok(prenotazione); // 200 OK O -> GlobalExceptionHandler -> PrenotazioneNotFoundException -> 404 NOT FOUND
    }

    // DELETE (Annullamento Stato): DELETE /api/prenotazioni/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean risultato = prenotazioneService.delete(id);
        
        return ResponseEntity.ok(risultato); // 200 OK O -> GlobalExceptionHandler -> PrenotazioneNotFoundException -> 404 NOT FOUND
    }
}