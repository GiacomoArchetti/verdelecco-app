package com.generation.giardini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.utente.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

}
