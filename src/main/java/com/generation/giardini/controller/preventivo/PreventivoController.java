package com.generation.giardini.controller.preventivo;

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

import com.generation.giardini.dto.PreventivoDTO;
import com.generation.giardini.service.preventivo.PreventivoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/preventivi")
@RequiredArgsConstructor
public class PreventivoController {

    private final PreventivoService preventivoService;

    // CREATE: POST /api/preventivi
    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody PreventivoDTO dto) {
        boolean risultato = preventivoService.create(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(risultato); // 201 CREATED O -> GlobalExceptionHandler -> PreventivoCreateException -> 400 BAD REQUEST
    }

    // READ ALL: GET /api/preventivi
    @GetMapping
    public ResponseEntity<List<PreventivoDTO>> readAll() {
        List<PreventivoDTO> preventivi = preventivoService.readAll();
        
        return ResponseEntity.ok(preventivi); // 200 OK con la lista (anche vuota)
    }

    // READ BY ID: GET /api/preventivi/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PreventivoDTO> readById(@PathVariable Long id) {
        PreventivoDTO preventivo = preventivoService.readById(id);
        
        return ResponseEntity.ok(preventivo); // 200 OK O -> GlobalExceptionHandler -> PreventivoNotFoundException -> 404 NOT FOUND
    }

    // DELETE (Soft Delete): DELETE /api/preventivi/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean risultato = preventivoService.delete(id);
        
        return ResponseEntity.ok(risultato); // 200 OK O -> GlobalExceptionHandler -> PreventivoNotFoundException -> 404 NOT FOUND
    }
}