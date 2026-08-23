package com.generation.giardini.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.prenotazione.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

	Page<Prenotazione> findByPreventivoUtenteEmailIgnoreCase(String email, Pageable pageable);
	
	boolean existsByPreventivoIdPreventivo(Long idPreventivo);

	List<Prenotazione> findByDataInterventoBetween(LocalDateTime start, LocalDateTime end);

}
