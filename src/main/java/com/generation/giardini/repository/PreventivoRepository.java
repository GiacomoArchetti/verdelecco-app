package com.generation.giardini.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.preventivo.Preventivo;

public interface PreventivoRepository extends JpaRepository<Preventivo, Long> {

	Page<Preventivo> findByUtenteEmailIgnoreCase(String email, Pageable pageable);

	Optional<Preventivo> findFirstByUtenteEmailIgnoreCaseOrderByDataEmissioneDesc(String email);

}
