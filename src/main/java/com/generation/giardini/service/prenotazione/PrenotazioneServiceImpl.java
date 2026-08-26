package com.generation.giardini.service.prenotazione;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.EventoCalendarioDTO;
import com.generation.giardini.dto.PrenotazioneDTO;
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
import com.generation.giardini.repository.RecensioneRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService {

    private final PrenotazioneMapper prenotazioneMapper;
    private final PrenotazioneRepository prenotazioneRepository;
    private final PreventivoRepository preventivoRepository;
    private final RecensioneRepository recensioneRepository;

    @Override
    public boolean create(PrenotazioneDTO dto) {
        if (dto == null) {
            throw new PrenotazioneCreateException("Impossibile creare la prenotazione: il DTO fornito è nullo.");
        }

        try {
            Prenotazione entity = prenotazioneMapper.toEntity(dto);
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
            
            boolean recensita = recensioneRepository.existsByPrenotazioneIdPrenotazione(e.getIdPrenotazione());
            lista.add(new PrenotazioneDTO(
                e.getIdPrenotazione(),
                e.getPreventivo() != null ? e.getPreventivo().getIdPreventivo() : null,
                e.getDataIntervento(),
                e.getIndirizzo(),
                e.getStato() != null ? e.getStato().name() : null,
                recensita
            ));
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public PrenotazioneDTO readById(Long id) {
        Prenotazione entity = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException(id));
                
                
        boolean recensita = recensioneRepository.existsByPrenotazioneIdPrenotazione(entity.getIdPrenotazione());
        return new PrenotazioneDTO(
            entity.getIdPrenotazione(),
            entity.getPreventivo() != null ? entity.getPreventivo().getIdPreventivo() : null,
            entity.getDataIntervento(),
            entity.getIndirizzo(),
            entity.getStato() != null ? entity.getStato().name() : null,
            recensita
        );
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

    @Override
    @Transactional(readOnly = true)
    public Page<PrenotazioneDTO> readAll(Pageable pageRequest) {
        Page<Prenotazione> pagePrenotazioni = prenotazioneRepository.findAll(pageRequest);
        
        return pagePrenotazioni.map(prenotazione -> new PrenotazioneDTO(
            prenotazione.getIdPrenotazione(),
            prenotazione.getPreventivo() != null ? prenotazione.getPreventivo().getIdPreventivo() : null,
            prenotazione.getDataIntervento(),
            prenotazione.getIndirizzo(),
            prenotazione.getStato() != null ? prenotazione.getStato().name() : null,
            recensioneRepository.existsByPrenotazioneIdPrenotazione(prenotazione.getIdPrenotazione())
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrenotazioneDTO> readByUtenteEmail(String email, Pageable pageable) {
        Page<Prenotazione> page = prenotazioneRepository.findByPreventivoUtenteEmailIgnoreCase(email, pageable);
        
        return page.map(prenotazione -> new PrenotazioneDTO(
            prenotazione.getIdPrenotazione(),
            prenotazione.getPreventivo() != null ? prenotazione.getPreventivo().getIdPreventivo() : null,
            prenotazione.getDataIntervento(),
            prenotazione.getIndirizzo(),
            prenotazione.getStato() != null ? prenotazione.getStato().name() : null,
            recensioneRepository.existsByPrenotazioneIdPrenotazione(prenotazione.getIdPrenotazione())
        ));
    }

    @Override
    public boolean complete(Long id) {
        Prenotazione entity = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new PrenotazioneNotFoundException(id));

        LocalDate dataIntervento = entity.getDataIntervento() != null 
                ? entity.getDataIntervento().toLocalDate() 
                : null;

        if (dataIntervento == null || dataIntervento.isAfter(LocalDate.now())) {
            return false;
        }

        entity.setStato(StatoPrenotazione.COMPLETATA);
        prenotazioneRepository.save(entity);
        return true;
    }

    @Override
    public List<EventoCalendarioDTO> getEventiCalendario(LocalDate start, LocalDate end) {
        // Trasforma LocalDate in LocalDateTime per matchare il tipo nel DB/Repository
        LocalDateTime startDateTime = start.atStartOfDay();           // 2026-08-01T00:00:00
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);            // 2026-08-31T23:59:59.999999999
        
        // 1. Recupera le prenotazioni esistenti dal database per il periodo selezionato
        List<Prenotazione> prenotazioni = prenotazioneRepository.findByDataInterventoBetween(startDateTime, endDateTime);

        // 2. Converte la lista di Entità in una lista di EventoCalendarioDTO usando gli Stream
        return prenotazioni.stream()
                // Se hai uno stato dell'appuntamento (es. ANNULLATO), puoi filtrarlo qui:
                .filter(p -> p.getStato() != StatoPrenotazione.ANNULLATA)
                .map(prenotazione -> new EventoCalendarioDTO(
                        "Occupato",                                                   // title
                        prenotazione.getDataIntervento().toLocalDate().toString(),          // start: trasformiamo LocalDateTime in LocalDate così il progetto risulti semplificato, strutturando il calendario in slots giornalieri e non in slots orari
                        null,                                                           // end (null se è un evento a giornata intera)
                        "background",                                               // display: colora l'intera cella del giorno
                        "#e74c3c"                                                   // color: rosso per giorno occupato
                ))
                .toList();
    }
}