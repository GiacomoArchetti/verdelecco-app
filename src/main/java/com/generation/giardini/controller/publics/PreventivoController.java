package com.generation.giardini.controller.publics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.service.servizio.ServizioService;

import lombok.RequiredArgsConstructor;

import com.generation.giardini.service.preventivo.PreventivoService;

@Controller
@RequestMapping("/preventivo")
@RequiredArgsConstructor
public class PreventivoController {

    private final ServizioService servizioService;
    private final PreventivoService preventivoService;

    //METODI GET

        //Restituisce la pagina del preventivo
        @GetMapping
        public String preventivo(Model model) {
            if (!model.containsAttribute("preventivoRequest")) {
                model.addAttribute("preventivoRequest", new PreventivoRequestDto());
            }

            // Carica i servizi attivi dal DB e costruisce opzioni leggibili per il frontend
            List<ServizioDTO> servizi = servizioService.readAllActive();
            List<Map<String,String>> serviziOptions = servizi.stream().map(s -> {
                Map<String,String> m = new HashMap<>();
                String value = s.nome();
                m.put("value", value);
                m.put("label", humanizeServiceName(value));
                return m;
            }).collect(Collectors.toList());

            model.addAttribute("serviziOptions", serviziOptions);

            return "preventivo";
        }

        //Restituisce la pagina di ringraziamento del preventivo
        @GetMapping("/inviato")
        public String preventivoInviato() {
            return "preventivo-success";
        }



        

        
        //Gestisce l'invio del modulo preventivo
        @PostMapping
        public String submitPreventivo(@ModelAttribute PreventivoRequestDto preventivoRequest) {
            preventivoService.createGuestRequest(preventivoRequest);
            return "redirect:/preventivo/inviato";
        }

        // Helper: trasforma i nomi enum in etichette leggibili in italiano
        private static String humanizeServiceName(String enumName) {
            if (enumName == null) return "";
            return switch(enumName) {
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
                    // Capitalize words
                    String[] parts = s.split(" ");
                    StringBuilder sb = new StringBuilder();
                    for (int i=0;i<parts.length;i++){
                        if (parts[i].length()>0) {
                            sb.append(parts[i].substring(0,1).toUpperCase()).append(parts[i].substring(1));
                        }
                        if (i < parts.length-1) sb.append(' ');
                    }
                    yield sb.toString();
                }
            };
        }

    }