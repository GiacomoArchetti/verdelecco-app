package com.generation.giardini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.prenotazione.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

}
