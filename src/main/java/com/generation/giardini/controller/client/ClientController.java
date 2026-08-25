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
import com.generation.giardini.exception.utente.UtenteNotFoundException;
import com.generation.giardini.service.preventivo.PreventivoService;
import com.generation.giardini.service.recensione.RecensioneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private static final int PAGE_SIZE = 5;

    private final PreventivoRepository preventivoRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final RecensioneService recensioneService;
    private final PreventivoService preventivoService;
    private final ServizioRepository servizioRepository;
    private final UtenteRepository utenteRepository;

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
            @RequestParam(value = "preventiviPage", defaultValue = "0") int preventiviPage,
            @RequestParam(value = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
            RedirectAttributes redirectAttributes) {
            
        preventivoRequest.setEmail(userDetails.getUsername());
        try {
            preventivoRequest.setTelefono(normalizzaTelefono(preventivoRequest.getTelefono()));
            aggiornaDatiContatto(userDetails.getUsername(), preventivoRequest.getTelefono(),
                    preventivoRequest.getIndirizzo());
            preventivoService.createGuestRequest(preventivoRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Richiesta di preventivo inviata correttamente.");
        } catch (RuntimeException exception) {
            log.error("Errore durante l'invio della richiesta di preventivo per l'utente: " + userDetails.getUsername(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", "Impossibile inviare la richiesta di preventivo. Verificare i dati e riprovare.");
        }
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#richiedi-preventivo", preventiviPage, prenotazioniPage);
    }

    @PostMapping("/profilo")
    public String aggiornaProfilo(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String indirizzo,
            @RequestParam(value = "preventiviPage", defaultValue = "0") int preventiviPage,
            @RequestParam(value = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
            RedirectAttributes redirectAttributes) {
            
        try {
            aggiornaDatiContatto(userDetails.getUsername(), telefono, indirizzo);
            redirectAttributes.addFlashAttribute("profileSuccessMessage", "Profilo aggiornato correttamente.");
        } catch (RuntimeException exception) {
            log.error("Errore durante l'aggiornamento del profilo per l'utente: " + userDetails.getUsername(), exception);
            redirectAttributes.addFlashAttribute("profileErrorMessage", 
                    "Si è verificato un errore durante il salvataggio dei dati. Riprova più tardi.");
        }
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#profilo", preventiviPage, prenotazioniPage);
    }

    @PostMapping("/prenotazioni/{id}/recensione")
    public String creaRecensione(@PathVariable("id") Long idPrenotazione,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Byte voto,
            @RequestParam(required = false) String commento,
            @RequestParam(value = "preventiviPage", defaultValue = "0") int preventiviPage,
            @RequestParam(value = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
            RedirectAttributes redirectAttributes) {
            
        try {
            recensioneService.createForPrenotazione(idPrenotazione, userDetails.getUsername(), voto, commento);
            redirectAttributes.addFlashAttribute("reviewSuccessMessage", "Recensione salvata correttamente.");
        } catch (RuntimeException exception) {
            log.error("Errore durante il salvataggio della recensione per la prenotazione: " + idPrenotazione, exception);
            redirectAttributes.addFlashAttribute("reviewErrorMessage", "Impossibile salvare la recensione. Assicurati di non averla già inviata e riprova.");
        }
        
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#prenotazioni", preventiviPage, prenotazioniPage);
    }

    private PreventivoRequestDto createRequestForUser(String email) {
        PreventivoRequestDto request = new PreventivoRequestDto();
        utenteRepository.findByEmailIgnoreCase(email).ifPresent(utente -> {
            request.setNome((utente.getNome() + " " + utente.getCognome()).trim());
            request.setEmail(utente.getEmail());
            request.setTelefono(normalizzaTelefono(utente.getTelefono()));
            // Se l'indirizzo del profilo è vuoto, recupera l'indirizzo dell'ultimo preventivo
            String indirizzo = utente.getIndirizzo();
            if (indirizzo == null || indirizzo.isBlank()) {
                indirizzo = preventivoRepository.findFirstByUtenteEmailIgnoreCaseOrderByDataEmissioneDesc(email)
                        .map(preventivo -> preventivo.getIndirizzo())
                        .orElse("");
            }
            request.setIndirizzo(indirizzo);
        });
        return request;
    }

    private void aggiornaDatiContatto(String email, String telefono, String indirizzo) {
        var utente = utenteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UtenteNotFoundException("Utente non trovato."));
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