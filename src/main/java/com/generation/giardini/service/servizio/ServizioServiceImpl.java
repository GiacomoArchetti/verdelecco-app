package com.generation.giardini.service.servizio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.entity.servizio.NomeServizio;
import com.generation.giardini.entity.servizio.Servizio;
import com.generation.giardini.exception.Servizio.ServizioCreateException;
import com.generation.giardini.exception.Servizio.ServizioNotFoundException;
import com.generation.giardini.mapper.ServizioMapper;
import com.generation.giardini.repository.ServizioRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServizioServiceImpl implements ServizioService {

    private final ServizioMapper servizioMapper;
    private final ServizioRepository servizioRepository;

    @Override
    public boolean create(ServizioDTO dto) {
        if (dto == null) {
            throw new ServizioCreateException("Impossibile creare il servizio: il DTO fornito è nullo.");
        }

        try {
            Servizio entity = servizioMapper.toEntity(dto);
            servizioRepository.save(entity);
            return true;
        } catch (Exception e) {
            String nomeServizio = dto.nome() != null ? dto.nome() : "Senza nome";
            throw new ServizioCreateException(nomeServizio, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServizioDTO> readAll() {
        return servizioRepository.findAll().stream()
                .map(servizioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServizioDTO> readAllActive() {
        return servizioRepository.findAll().stream()
                .filter(servizio -> Boolean.TRUE.equals(servizio.getAttivo()))
                .map(servizioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServizioDTO> readAllNotActive() {
        return servizioRepository.findAll().stream()
                .filter(servizio -> Boolean.FALSE.equals(servizio.getAttivo()))
                .map(servizioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ServizioDTO readById(Long id) {
        Servizio entity = servizioRepository.findById(id)
                .orElseThrow(() -> new ServizioNotFoundException(id));
        return servizioMapper.toDto(entity);
    }

    @Override
    public boolean delete(Long id) {
        Servizio entity = servizioRepository.findById(id)
                .orElseThrow(() -> new ServizioNotFoundException(id));

        entity.setAttivo(false);
        servizioRepository.save(entity);

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> readAllAttiviOptions() {
        return servizioRepository.findAll().stream()
                .filter(servizio -> Boolean.TRUE.equals(servizio.getAttivo()))
                .map(servizio -> {
                    Map<String, String> option = new HashMap<>();
                    String nomeEnumStr = servizio.getNome() != null ? servizio.getNome().name() : "";
                    option.put("value", nomeEnumStr);
                    option.put("label", humanizeServiceName(nomeEnumStr));
                    return option;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> readDetailedServizioOptions() {
        return servizioRepository.findAll().stream()
                .filter(servizio -> Boolean.TRUE.equals(servizio.getAttivo()))
                .map(servizio -> {
                    Map<String, String> option = new HashMap<>();
                    NomeServizio nomeEnum = servizio.getNome();
                    
                    if (nomeEnum != null) {
                        option.put("value", nomeEnum.name());
                        option.put("label", nomeEnum.getLabel());
                        option.put("image", nomeEnum.getImage());
                        option.put("descrizione", nomeEnum.getDescrizione());
                    } else {
                        option.put("value", "");
                        option.put("label", "");
                        option.put("image", "/images/gardening.png");
                        option.put("descrizione", servizio.getDescrizione() != null ? servizio.getDescrizione() : "");
                    }
                    
                    return option;
                })
                .collect(Collectors.toList());
    }

    /**
     * Converte il nome del servizio in formato enum in una descrizione
     * leggibile e adatta alla visualizzazione all'utente.
     */
    private static String humanizeServiceName(String enumName) {
        if (enumName == null || enumName.isEmpty())
            return "";
        return switch (enumName) {
            case "TAGLIO_ERBA" -> "Taglio erba";
            case "POTATURA" -> "Potatura";
            case "SEMINA" -> "Semina";
            case "PULIZIA_GIARDINO" -> "Pulizia giardino";
            case "MANUTENZIONE_TAPPETO_ERBOSO" -> "Manutenzione tappeto erboso";
            case "SFALCIO_RIVE_E_SCARPATE" -> "Sfalcio rive e scarpate";
            case "POTATURA_ALBERI_DA_FRUTTO" -> "Potatura alberi da frutto";
            case "POTATURA_ALBERI_ORNAMENTALI" -> "Potatura alberi ornamentali";
            case "POTATURA_SIEPI" -> "Potatura siepi";
            default -> {
                String s = enumName.replace('_', ' ').toLowerCase();
                String[] parts = s.split(" ");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].length() > 0) {
                        sb.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1));
                    }
                    if (i < parts.length - 1)
                        sb.append(' ');
                }
                yield sb.toString();
            }
        };
    }
}