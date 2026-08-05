package com.generation.giardini.controller.utente;

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

import com.generation.giardini.dto.UtenteDTO;
import com.generation.giardini.service.utente.UtenteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/utenti")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;

    // CREATE: POST /api/utenti
    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody UtenteDTO dto) {
        boolean risultato = utenteService.create(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(risultato); // 201 CREATED O -> GlobalExceptionHandler -> UtenteCreateException -> 400 BAD REQUEST
    }

    // READ ALL: GET /api/utenti
    @GetMapping
    public ResponseEntity<List<UtenteDTO>> readAll() {
        List<UtenteDTO> utenti = utenteService.readAll();
        
        return ResponseEntity.ok(utenti); // 200 OK con la lista (anche vuota)
    }

    // READ BY ID: GET /api/utenti/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> readById(@PathVariable Long id) {
        UtenteDTO utente = utenteService.readById(id);
        
        return ResponseEntity.ok(utente); // 200 OK O -> GlobalExceptionHandler -> UtenteNotFoundException -> 404 NOT FOUND
    }

    // DELETE (Soft Delete): DELETE /api/utenti/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean risultato = utenteService.delete(id);
        
        return ResponseEntity.ok(risultato); // 200 OK O -> GlobalExceptionHandler -> UtenteNotFoundException -> 404 NOT FOUND
    }
}