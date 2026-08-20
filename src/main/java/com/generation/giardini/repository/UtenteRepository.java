package com.generation.giardini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.utente.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
	Optional<Utente> findByEmail(String email);
	Optional<Utente> findByEmailIgnoreCase(String email);
}
