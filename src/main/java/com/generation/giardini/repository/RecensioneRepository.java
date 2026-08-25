package com.generation.giardini.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.entity.Recensione;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

	boolean existsByPrenotazioneIdPrenotazione(Long idPrenotazione);

	@Query("""
        SELECT new com.generation.giardini.dto.RecensioneDTO(
            r.idRecensione,
            p.idPrenotazione,
            u.email,
            r.voto,
            r.commento,
            r.dataRecensione
        )
        FROM Recensione r
        JOIN r.prenotazione p
        JOIN p.preventivo pr
        JOIN pr.utente u
    """)
    Page<RecensioneDTO> findAllCustomDTO(Pageable pageable);

}
