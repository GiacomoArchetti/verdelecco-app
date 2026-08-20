package com.generation.giardini.controller.publics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.giardini.dto.RecensioneDTO;
import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.service.recensione.RecensioneService;
import com.generation.giardini.service.servizio.ServizioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController1 {

    private final ServizioService servizioService;
    private final RecensioneService recensioneService;

    // Restituisce la pagina della home
    @GetMapping
    public String home(Model model) {

        // Carica servizi attivi e prepara opzioni (value,label)
        List<ServizioDTO> servizi = servizioService.readAllActive();
        List<Map<String, String>> serviziOptions = servizi.stream()
                                                            .map(s -> {
                                                                        Map<String, String> m = new HashMap<>();
                                                                        m.put("value", s.nome());
                                                                        m.put("label", humanizeServiceName(s.nome()));
                                                                        return m;
                                                                    })
                                                            .collect(Collectors.toList());

        model.addAttribute("serviziOptions", serviziOptions);
        
        List<RecensioneDTO> recensioni = recensioneService.readAll();
        model.addAttribute("recensioni", recensioni);

        return "home";
    }

    /**
     * Converte il nome del servizio in formato enum in una descrizione
     * leggibile e adatta alla visualizzazione all'utente.
     *
     * <p>
     * Per i servizi definiti esplicitamente viene utilizzata una
     * descrizione personalizzata; per gli altri valori viene applicata
     * una conversione automatica da {@code ENUM_NAME} a testo leggibile.
     * </p>
     *
     * @param enumName nome del servizio nel formato enum
     * @return nome del servizio in formato leggibile, oppure stringa vuota
     *         se {@code enumName} è {@code null}
     */
    private static String humanizeServiceName(String enumName) {
        if (enumName == null)
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