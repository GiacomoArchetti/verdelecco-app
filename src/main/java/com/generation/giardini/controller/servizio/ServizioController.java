package com.generation.giardini.controller.servizio;

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

import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.service.servizio.ServizioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servizi")
@RequiredArgsConstructor
public class ServizioController {

    private final ServizioService servizioService;

    // CREATE: POST /api/servizi
    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody ServizioDTO dto) {
        boolean risultato = servizioService.create(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(risultato); // 201 CREATED O -> GlobalExceptionHandler -> ServizioCreateException -> 400 BAD REQUEST
    }

    // READ ALL: GET /api/servizi
    @GetMapping
    public ResponseEntity<List<ServizioDTO>> readAll() {
        List<ServizioDTO> servizi = servizioService.readAll();
        
        return ResponseEntity.ok(servizi); // 200 OK con la lista (anche vuota)
    }

    // READ BY ID: GET /api/servizi/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ServizioDTO> readById(@PathVariable Long id) {
        ServizioDTO servizio = servizioService.readById(id);
        
        return ResponseEntity.ok(servizio); // 200 OK O -> GlobalExceptionHandler -> ServizioNonTrovatoException -> 404 NOT FOUND
    }

    // DELETE (Soft Delete): DELETE /api/servizi/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean risultato = servizioService.delete(id);
        
        return ResponseEntity.ok(risultato); // 200 OK O -> GlobalExceptionHandler -> ServizioNonTrovatoException -> 404 NOT FOUND
    }
}