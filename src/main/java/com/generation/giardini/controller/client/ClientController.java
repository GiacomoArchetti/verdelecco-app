package com.generation.giardini.controller.client;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.generation.giardini.repository.PrenotazioneRepository;
import com.generation.giardini.repository.PreventivoRepository;
import com.generation.giardini.repository.ServizioRepository;
import com.generation.giardini.repository.UtenteRepository;
import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.dto.ServizioDTO;
import com.generation.giardini.service.preventivo.PreventivoService;
import com.generation.giardini.service.recensione.RecensioneService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private static final int PAGE_SIZE = 5;

    private final PreventivoRepository preventivoRepository;
    private final PrenotazioneRepository prenotazioneRepository;
        private final RecensioneService recensioneService;
        private final PreventivoService preventivoService;
        private final ServizioRepository servizioRepository;
        private final UtenteRepository utenteRepository;

    //METODI GET

        //Restituisce la pagina del portale cliente
        @GetMapping
        public String utente(Model model,
                @AuthenticationPrincipal UserDetails userDetails,
                @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage) {
            String email = userDetails.getUsername();
            int safePreventiviPage = Math.max(preventiviPage, 0);
            int safePrenotazioniPage = Math.max(prenotazioniPage, 0);

            model.addAttribute("preventivi", preventivoRepository.findByUtenteEmailIgnoreCase(
                    email, PageRequest.of(safePreventiviPage, PAGE_SIZE,
                            Sort.by(Sort.Direction.DESC, "dataEmissione"))));
            model.addAttribute("prenotazioni", prenotazioneRepository.findByPreventivoUtenteEmailIgnoreCase(
                    email, PageRequest.of(safePrenotazioniPage, PAGE_SIZE,
                            Sort.by(Sort.Direction.ASC, "dataIntervento"))));
                        model.addAttribute("preventivoRequest", createRequestForUser(email));
                        var utente = utenteRepository.findByEmailIgnoreCase(email)
                                        .orElseThrow(() -> new IllegalStateException("Utente non trovato."));
                        model.addAttribute("nomeUtente", utente.getNome());
                        model.addAttribute("cognomeUtente", utente.getCognome());
                            model.addAttribute("telefonoUtente", normalizzaTelefono(utente.getTelefono()));
                        String indirizzo = utente.getIndirizzo();
                        if (indirizzo == null || indirizzo.isBlank()) {
                                indirizzo = preventivoRepository.findFirstByUtenteEmailIgnoreCaseOrderByDataEmissioneDesc(email)
                                                .map(preventivo -> preventivo.getIndirizzo())
                                                .orElse("");
                        }
                        model.addAttribute("indirizzoUtente", indirizzo);
                        model.addAttribute("serviziOptions", servizioRepository.findAll().stream()
                                        .filter(servizio -> Boolean.TRUE.equals(servizio.getAttivo()))
                                                        .map(servizio -> new ServizioDTO(servizio.getIdServizio(), servizio.getNome().name(),
                                                                        servizio.getPrezzoAlMq(), servizio.getMinutiAlMq(),
                                                                        servizio.getDescrizione(), servizio.getAttivo()))
                                        .map(servizio -> {
                                                Map<String, String> option = new HashMap<>();
                                                option.put("value", servizio.nome());
                                                option.put("label", humanizeServiceName(servizio.nome()));
                                                return option;
                                        }).collect(Collectors.toList()));
            return "client";
        }

                @PostMapping("/preventivo")
                public String richiediPreventivo(@ModelAttribute PreventivoRequestDto preventivoRequest,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
                        preventivoRequest.setEmail(userDetails.getUsername());
                        try {
                                preventivoRequest.setTelefono(normalizzaTelefono(preventivoRequest.getTelefono()));
                                aggiornaDatiContatto(userDetails.getUsername(), preventivoRequest.getTelefono(),
                                                preventivoRequest.getIndirizzo());
                                preventivoService.createGuestRequest(preventivoRequest);
                                redirectAttributes.addFlashAttribute("successMessage", "Richiesta di preventivo inviata correttamente.");
                        } catch (RuntimeException exception) {
                                redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
                        }
                        return "redirect:/client#preventivi";
                }

        @PostMapping("/profilo")
        public String aggiornaProfilo(@AuthenticationPrincipal UserDetails userDetails,
                        @RequestParam(required = false) String telefono,
                        @RequestParam(required = false) String indirizzo,
                        RedirectAttributes redirectAttributes) {
                try {
                        aggiornaDatiContatto(userDetails.getUsername(), telefono, indirizzo);
                        redirectAttributes.addFlashAttribute("successMessage", "Profilo aggiornato correttamente.");
                } catch (RuntimeException exception) {
                        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
                }
                return "redirect:/client#profilo";
        }

        @PostMapping("/prenotazioni/{id}/recensione")
                public String creaRecensione(@PathVariable("id") Long idPrenotazione,
                                @AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam Byte voto,
                                @RequestParam(required = false) String commento,
                                RedirectAttributes redirectAttributes) {
                        try {
                                recensioneService.createForPrenotazione(idPrenotazione, userDetails.getUsername(), voto, commento);
                                redirectAttributes.addFlashAttribute("successMessage", "Recensione salvata correttamente.");
                        } catch (RuntimeException exception) {
                                redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
                        }
                        return "redirect:/client#prenotazioni";
                }

                private PreventivoRequestDto createRequestForUser(String email) {
                        PreventivoRequestDto request = new PreventivoRequestDto();
                        utenteRepository.findByEmailIgnoreCase(email).ifPresent(utente -> {
                                request.setNome((utente.getNome() + " " + utente.getCognome()).trim());
                                request.setEmail(utente.getEmail());
                                request.setTelefono(normalizzaTelefono(utente.getTelefono()));
                                request.setIndirizzo(utente.getIndirizzo());
                        });
                        return request;
                }

        private void aggiornaDatiContatto(String email, String telefono, String indirizzo) {
                var utente = utenteRepository.findByEmailIgnoreCase(email)
                                .orElseThrow(() -> new IllegalStateException("Utente non trovato."));
                utente.setTelefono(normalizzaTelefono(telefono));
                utente.setIndirizzo(indirizzo == null ? null : indirizzo.trim());
                utenteRepository.save(utente);
        }

        private static String normalizzaTelefono(String telefono) {
                if (telefono == null || telefono.isBlank()) {
                        return null;
                }
                String valore = telefono.trim().replaceAll("\\s+", " ");
                if (valore.startsWith("0039")) {
                        valore = "+39" + valore.substring(4).trim();
                }
                return valore.startsWith("+39") ? valore : "+39 " + valore;
        }

                private static String humanizeServiceName(String enumName) {
                        if (enumName == null) {
                                return "";
                        }
                        String[] parts = enumName.replace('_', ' ').toLowerCase().split(" ");
                        StringBuilder result = new StringBuilder();
                        for (String part : parts) {
                                if (!part.isEmpty()) {
                                        if (result.length() > 0) {
                                                result.append(' ');
                                        }
                                        result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
                                }
                        }
                        return result.toString();
                }
        
}