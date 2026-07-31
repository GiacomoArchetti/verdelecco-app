package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.UtenteDTO;
import com.generation.giardini.entity.utente.Utente;

@Component
public class UtenteMapper {

    public UtenteDTO toDto(Utente entity) {
        if(entity == null) {
            return null;
        }
        return new UtenteDTO(
            entity.getIdUtente(),
            entity.getNome(),
            entity.getCognome(),
            entity.getEmail(),
            entity.getTelefono(),
            entity.getAttivo(),
            entity.getRuolo()
        );
    }

    public Utente toEntity(UtenteDTO dto) {
        if(dto == null) {
            return null;
        }
        Utente utente = new Utente();
        utente.setIdUtente(dto.idUtente());
        utente.setNome(dto.nome());
        utente.setCognome(dto.cognome());
        utente.setEmail(dto.email());
        utente.setTelefono(dto.telefono());
        if(dto.attivo() != null) {
            utente.setAttivo(dto.attivo());
        }
        if(dto.ruolo() != null) {
            utente.setRuolo(dto.ruolo());
        }
        // Controlli per prevenire il fatto che se attivo o ruolo sono null, il valore di default non viene sovrascritto con null
        
        return utente;
    }

}
