package com.generation.giardini.controller.client;

import java.security.Principal;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.dto.UtenteDTO;
import com.generation.giardini.service.prenotazione.PrenotazioneService;
import com.generation.giardini.service.preventivo.PreventivoService;
import com.generation.giardini.service.recensione.RecensioneService;
import com.generation.giardini.service.servizio.ServizioService;
import com.generation.giardini.service.utente.UtenteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private static final int PAGE_SIZE = 5;

    private final PrenotazioneService prenotazioneService;
    private final RecensioneService recensioneService;
    private final PreventivoService preventivoService;
    private final ServizioService servizioService;
    private final UtenteService utenteService;

    @GetMapping
    public String utente(Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
            @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage) {
            
        String email = userDetails.getUsername();
        int safePreventiviPage = Math.max(preventiviPage, 0);
        int safePrenotazioniPage = Math.max(prenotazioniPage, 0);

        model.addAttribute("preventivi", preventivoService.readByUtenteEmail(
                email, PageRequest.of(safePreventiviPage, PAGE_SIZE,
                        Sort.by(Sort.Direction.DESC, "dataEmissione"))));
        model.addAttribute("prenotazioni", prenotazioneService.readByUtenteEmail(
                email, PageRequest.of(safePrenotazioniPage, PAGE_SIZE,
                        Sort.by(Sort.Direction.ASC, "dataIntervento"))));
        
        model.addAttribute("preventivoRequest", utenteService.createPreventivoRequestForUser(email));
        
        UtenteDTO utente = utenteService.readByEmail(email);
        
        model.addAttribute("nomeUtente", utente.nome());
        model.addAttribute("cognomeUtente", utente.cognome());
        model.addAttribute("telefonoUtente", utente.telefono());
        
        String indirizzo = utente.indirizzo();
        if (indirizzo == null || indirizzo.isBlank()) {
            indirizzo = preventivoService.readLatestIndirizzoByUtenteEmail(email);
        }
        model.addAttribute("indirizzoUtente", indirizzo);
        
        model.addAttribute("serviziOptions", servizioService.readAllAttiviOptions());
                
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
            utenteService.updateDatiContatto(userDetails.getUsername(), preventivoRequest.getTelefono(),
                    preventivoRequest.getIndirizzo());
            preventivoService.createGuestRequest(preventivoRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Richiesta di preventivo inviata correttamente.");
        } catch (RuntimeException exception) {
            log.error("Errore durante l'invio della richiesta di preventivo per l'utente: " + userDetails.getUsername(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", "Impossibile inviare la richiesta di preventivo. Verificare i dati e riprovare.");
        }
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#richiedi-preventivo", preventiviPage, prenotazioniPage);
    }

    @PostMapping("/preventivi/{id}/cancel")
    public String cancelPreventivoAsClient(@PathVariable Long id, Principal principal, 
                                           @RequestParam(value = "preventiviPage", defaultValue = "0") int preventiviPage,
                                           @RequestParam(value = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                           RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        String emailUtente = principal.getName();
        
        boolean successo = preventivoService.cancelAsClient(id, emailUtente);
        
        if (successo) {
            redirectAttributes.addFlashAttribute("successMessage", "Preventivo annullato con successo.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Impossibile annullare il preventivo.");
        }
        
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#preventivi", preventiviPage, prenotazioniPage);
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

    /**
     * Permette al cliente autenticato di annullare una propria prenotazione.
     * 
     * @param id L'identificativo della prenotazione da annullare
     * @param principal Oggetto per identificare l'utente attualmente loggato
     * @param preventiviPage Pagina corrente della tabella preventivi
     * @param prenotazioniPage Pagina corrente della tabella prenotazioni
     * @param redirectAttributes Attributi per messaggi flash di feedback
     * @return Redirect alla pagina del profilo/area riservata del cliente con mantenimento della paginazione
     */
    @PostMapping("/prenotazioni/{id}/cancel")
    public String cancelPrenotazioneAsClient(@PathVariable Long id, Principal principal, 
                                           @RequestParam(value = "preventiviPage", defaultValue = "0") int preventiviPage,
                                           @RequestParam(value = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                           RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        
        String emailUtente = principal.getName();
        
        // Esegue la logica di business verificando che la prenotazione appartenga al cliente
        boolean successo = prenotazioneService.cancelAsClient(id, emailUtente);
        
        if (successo) {
            redirectAttributes.addFlashAttribute("successMessage", "Prenotazione annullata con successo.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Impossibile annullare la prenotazione.");
        }
        
        // Reindirizza alla tabella prenotazioni del portale cliente preservando la paginazione
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#prenotazioni", preventiviPage, prenotazioniPage);
    }

    @PostMapping("/profilo")
    public String aggiornaProfilo(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String indirizzo,
            @RequestParam(value = "preventiviPage", defaultValue = "0") int preventiviPage,
            @RequestParam(value = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
            RedirectAttributes redirectAttributes) {
            
        try {
            utenteService.updateDatiContatto(userDetails.getUsername(), telefono, indirizzo);
            redirectAttributes.addFlashAttribute("profileSuccessMessage", "Profilo aggiornato correttamente.");
        } catch (RuntimeException exception) {
            log.error("Errore durante l'aggiornamento del profilo per l'utente: " + userDetails.getUsername(), exception);
            redirectAttributes.addFlashAttribute("profileErrorMessage", 
                    "Si è verificato un errore durante il salvataggio dei dati. Riprova più tardi.");
        }
        return String.format("redirect:/client?preventiviPage=%d&prenotazioniPage=%d#profilo", preventiviPage, prenotazioniPage);
    }
}