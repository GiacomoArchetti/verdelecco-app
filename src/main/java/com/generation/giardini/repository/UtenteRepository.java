package com.generation.giardini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

}
