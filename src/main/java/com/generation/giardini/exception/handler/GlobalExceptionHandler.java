package com.generation.giardini.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.generation.giardini.exception.preventivo.PreventivoCreateException;
import com.generation.giardini.exception.preventivo.PreventivoNotFoundException;
import com.generation.giardini.exception.Servizio.ServizioCreateException;
import com.generation.giardini.exception.Servizio.ServizioNotFoundException;
import com.generation.giardini.exception.prenotazione.PrenotazioneCreateException;
import com.generation.giardini.exception.prenotazione.PrenotazioneNotFoundException;
import com.generation.giardini.exception.recensione.RecensioneCreateException;
import com.generation.giardini.exception.recensione.RecensioneNotFoundException;
import com.generation.giardini.exception.utente.UtenteCreateException;
import com.generation.giardini.exception.utente.UtenteNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- UTENTE ---
    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<String> handleUtenteNotFound(UtenteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UtenteCreateException.class)
    public ResponseEntity<String> handleUtenteCreate(UtenteCreateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // --- SERVIZIO ---
    @ExceptionHandler(ServizioNotFoundException.class)
    public ResponseEntity<String> handleServizioNotFound(ServizioNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ServizioCreateException.class)
    public ResponseEntity<String> handleServizioCreate(ServizioCreateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // --- PREVENTIVO ---
    @ExceptionHandler(PreventivoNotFoundException.class)
    public ResponseEntity<String> handlePreventivoNotFound(PreventivoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PreventivoCreateException.class)
    public ResponseEntity<String> handlePreventivoCreate(PreventivoCreateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // --- PRENOTAZIONE ---
    @ExceptionHandler(PrenotazioneNotFoundException.class)
    public ResponseEntity<String> handlePrenotazioneNotFound(PrenotazioneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PrenotazioneCreateException.class)
    public ResponseEntity<String> handlePrenotazioneCreate(PrenotazioneCreateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // --- RECENSIONE ---
    @ExceptionHandler(RecensioneNotFoundException.class)
    public ResponseEntity<String> handleRecensioneNotFound(RecensioneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RecensioneCreateException.class)
    public ResponseEntity<String> handleRecensioneCreate(RecensioneCreateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}