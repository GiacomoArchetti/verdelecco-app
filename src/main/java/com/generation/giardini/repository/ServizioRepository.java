package com.generation.giardini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.generation.giardini.entity.servizio.Servizio;
import com.generation.giardini.entity.servizio.NomeServizio;

public interface ServizioRepository extends JpaRepository<Servizio, Long> {

	Optional<Servizio> findFirstByNomeAndAttivoTrueOrderByPrezzoAlMqAsc(NomeServizio nome);

}
