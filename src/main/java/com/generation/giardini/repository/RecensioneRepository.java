package com.generation.giardini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.Recensione;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

	boolean existsByPrenotazioneIdPrenotazione(Long idPrenotazione);

}
