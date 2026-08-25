package com.generation.giardini.service.recensione;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.entity.Recensione;
import com.generation.giardini.entity.prenotazione.StatoPrenotazione;
import com.generation.giardini.exception.recensione.RecensioneCreateException;
import com.generation.giardini.exception.recensione.RecensioneNotFoundException;
import com.generation.giardini.mapper.RecensioneMapper;
import com.generation.giardini.repository.RecensioneRepository;
import com.generation.giardini.repository.PrenotazioneRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService {

    private final RecensioneMapper recensioneMapper;
    private final RecensioneRepository recensioneRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    @Override
    public boolean create(RecensioneDTO dto) {
        if (dto == null) {
            throw new RecensioneCreateException("Impossibile creare la recensione: il DTO fornito è nullo.");
        }

        try {
            Recensione entity = recensioneMapper.toEntity(dto);
            recensioneRepository.save(entity);
            return true;
            
        } catch (Exception e) {
            throw new RecensioneCreateException("Errore imprevisto durante la creazione della recensione.", e);
        }
    }

    @Override
    public boolean createForPrenotazione(Long idPrenotazione, String email, Byte voto, String commento) {
        if (idPrenotazione == null || voto == null || voto < 1 || voto > 5) {
            throw new RecensioneCreateException("Il voto deve essere compreso tra 1 e 5.");
        }

        var prenotazione = prenotazioneRepository.findById(idPrenotazione)
                .orElseThrow(() -> new RecensioneNotFoundException("Prenotazione non trovata."));

        if (prenotazione.getPreventivo() == null
                || prenotazione.getPreventivo().getUtente() == null
                || !prenotazione.getPreventivo().getUtente().getEmail().equalsIgnoreCase(email)) {
            throw new RecensioneCreateException("Non puoi recensire questa prenotazione.");
        }
        if (prenotazione.getStato() != StatoPrenotazione.COMPLETATA) {
            throw new RecensioneCreateException("Puoi recensire solo una prenotazione con stato completata.");
        }
        if (recensioneRepository.existsByPrenotazioneIdPrenotazione(idPrenotazione)) {
            throw new RecensioneCreateException("Questa prenotazione ha già una recensione.");
        }

        Recensione recensione = new Recensione();
        recensione.setPrenotazione(prenotazione);
        recensione.setVoto(voto);
        recensione.setCommento(commento == null ? null : commento.trim());
        recensione.setDataRecensione(LocalDateTime.now());
        recensioneRepository.save(recensione);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecensioneDTO> readAll() {
        List<RecensioneDTO> lista = new ArrayList<>();
        for (Recensione e : recensioneRepository.findAll()) {
            lista.add(recensioneMapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public RecensioneDTO readById(Long id) {
        Recensione entity = recensioneRepository.findById(id)
                .orElseThrow(() -> new RecensioneNotFoundException(id));
                
        return recensioneMapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Recensione entity = recensioneRepository.findById(id)
                .orElseThrow(() -> new RecensioneNotFoundException(id));

        recensioneRepository.delete(entity); //Cancello fisicamente la recensione anche dal db perchè non ha valore storico

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecensioneDTO> readAll(Pageable pageRequest) {
        return recensioneRepository.findAllCustomDTO(pageRequest);
    }
}