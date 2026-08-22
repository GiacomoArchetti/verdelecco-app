package com.generation.giardini.service.prenotazione;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.PrenotazioneDTO;
import com.generation.giardini.dto.PreventivoDTO;
import com.generation.giardini.entity.prenotazione.Prenotazione;
import com.generation.giardini.entity.prenotazione.StatoPrenotazione;
import com.generation.giardini.entity.preventivo.Preventivo;
import com.generation.giardini.entity.preventivo.StatoPreventivo;
import com.generation.giardini.exception.prenotazione.PrenotazioneCreateException;
import com.generation.giardini.exception.prenotazione.PrenotazioneNotFoundException;
import com.generation.giardini.exception.preventivo.PreventivoNotFoundException;
import com.generation.giardini.mapper.PrenotazioneMapper;
import com.generation.giardini.repository.PrenotazioneRepository;
import com.generation.giardini.repository.PreventivoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService {

    private final PrenotazioneMapper mapper;
    private final PrenotazioneRepository prenotazioneRepository;
    private final PreventivoRepository preventivoRepository;

    @Override
    public boolean create(PrenotazioneDTO dto) {
        if (dto == null) {
            throw new PrenotazioneCreateException("Impossibile creare la prenotazione: il DTO fornito è nullo.");
        }

        try {
            Prenotazione entity = mapper.toEntity(dto);
            prenotazioneRepository.save(entity);
            return true;
            
        } catch (Exception e) {
            throw new PrenotazioneCreateException("Errore imprevisto durante la creazione della prenotazione.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrenotazioneDTO> readAll() {
        List<PrenotazioneDTO> lista = new ArrayList<>();
        for (Prenotazione e : prenotazioneRepository.findAll()) {
            lista.add(mapper.toDto(e));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public PrenotazioneDTO readById(Long id) {
        Prenotazione entity = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException(id));
                
        return mapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Prenotazione entity = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException(id));

        entity.setStato(StatoPrenotazione.ANNULLATA); // Gestione dello stato in base all'enum presente nel DB (ANNULLATA)
        prenotazioneRepository.save(entity);

        return true;
    }

    @Transactional
    public boolean createFromPreventivo(Long preventivoId) {
        try {
            // a. Recupera il preventivo
            Preventivo preventivo = preventivoRepository.findById(preventivoId)
                    .orElseThrow(() -> new PreventivoNotFoundException(preventivoId));

            // b. Controllo di validità: deve essere ACCETTATO
            if (preventivo.getStatoPreventivo() != StatoPreventivo.ACCETTATO) {
                return false;
            }

            // c. Evita di creare prenotazioni duplicate per lo stesso preventivo
            if (prenotazioneRepository.existsByPreventivoIdPreventivo(preventivoId)) {
                return false;
            }

            // d. Istanzia e popola la nuova Prenotazione
            Prenotazione nuovaPrenotazione = new Prenotazione();
            nuovaPrenotazione.setPreventivo(preventivo);
            nuovaPrenotazione.setIndirizzo(preventivo.getIndirizzo());
            nuovaPrenotazione.setDataIntervento(preventivo.getDataIntervento());
            nuovaPrenotazione.setStato(StatoPrenotazione.PROGRAMMATA);

            // e. Salva a DB e verifica che l'oggetto salvato non sia null
            Prenotazione salvata = prenotazioneRepository.save(nuovaPrenotazione);
            return salvata != null && salvata.getIdPrenotazione() != null;

        } catch (Exception e) {
            throw new PrenotazioneCreateException("Errore imprevisto durante la creazione della prenotazione da preventivo.", e);
        }
    }
}