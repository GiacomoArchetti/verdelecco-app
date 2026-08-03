package com.generation.giardini.mapper;

import org.springframework.stereotype.Component;

import com.generation.giardini.dto.UtenteDTO;
import com.generation.giardini.entity.utente.Ruolo;
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
            entity.getRuolo() != null ? entity.getRuolo().name() : null 
        );
    }

    public Utente toEntity(UtenteDTO dto) {
        if(dto == null) {
            return null;
        }
        Utente entity = new Utente();
        entity.setIdUtente(dto.idUtente());
        entity.setNome(dto.nome());
        entity.setCognome(dto.cognome());
        entity.setEmail(dto.email());
        entity.setTelefono(dto.telefono());
        if(dto.attivo() != null) {
            entity.setAttivo(dto.attivo());
        }
        if(dto.ruolo() == null) {
            entity.setRuolo(Ruolo.UTENTE);
        }else{
            entity.setRuolo(switch (dto.ruolo().toUpperCase()){
                case "ADMIN" -> Ruolo.ADMIN;
                default -> Ruolo.UTENTE;
            }); //Sintassi switch da java 14+
        }
        // Controlli per prevenire il fatto che se attivo o ruolo sono null, il valore di default non viene sovrascritto con null
        
        return entity;
    }

}