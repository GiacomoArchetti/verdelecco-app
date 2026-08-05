package com.generation.giardini.controller.recensione;

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

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.service.recensione.RecensioneService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recensioni")
@RequiredArgsConstructor
public class RecensioneController {
    
    private final RecensioneService recensioneService;

    // CREATE: POST /api/preventivi
    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody RecensioneDTO dto) {
        boolean risultato = recensioneService.create(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(risultato); // 201 CREATED O -> GlobalExceptionHandler -> RecensioneCreateException -> 400 BAD REQUEST
    }

    // READ ALL: GET /api/preventivi
    @GetMapping
    public ResponseEntity<List<RecensioneDTO>> readAll() {
        List<RecensioneDTO> recensioni = recensioneService.readAll();
        
        return ResponseEntity.ok(recensioni); // 200 OK con la lista (anche vuota)
    }

    // READ BY ID: GET /api/preventivi/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RecensioneDTO> readById(@PathVariable("id") Long id) {
        RecensioneDTO recensione = recensioneService.readById(id);
        
        return ResponseEntity.ok(recensione); // 200 OK O -> GlobalExceptionHandler -> RecensioneNotFoundException -> 404 NOT FOUND
    }

    // DELETE (Soft Delete): DELETE /api/preventivi/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        boolean risultato = recensioneService.delete(id);
        
        return ResponseEntity.ok(risultato); // 200 OK O -> GlobalExceptionHandler -> RecensioneNotFoundException -> 404 NOT FOUND
    }

}
