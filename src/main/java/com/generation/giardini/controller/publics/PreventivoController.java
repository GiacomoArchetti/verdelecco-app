package com.generation.giardini.controller.publics;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.service.servizio.ServizioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.generation.giardini.service.preventivo.PreventivoService;

@Controller
@RequestMapping("/preventivo")
@RequiredArgsConstructor
@Slf4j
public class PreventivoController {

    private final ServizioService servizioService;
    private final PreventivoService preventivoService;

    //METODI GET

        // Restituisce la pagina del preventivo
        @GetMapping
        public String preventivo(Model model, Authentication authentication) {

            // Controllo autenticazione per Redirect RBAC
            if (authentication != null && authentication.isAuthenticated() 
                    && !(authentication instanceof AnonymousAuthenticationToken)) {
                
                // Se è un utente cliente autenticato, reindirizza al suo portale dedicato
                if (authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_UTENTE"))) {
                    return "redirect:/client#richiedi-preventivo";
                }
                
                // Se è l'amministratore, reindirizza alla dashboard admin
                if (authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    return "redirect:/admin";
                }
            }

            if (!model.containsAttribute("preventivoRequest")) {
                model.addAttribute("preventivoRequest", new PreventivoRequestDto());
            }

            // Carica le opzioni dei servizi pulite direttamente tramite il service
            model.addAttribute("serviziOptions", servizioService.readAllAttiviOptions());

            return "preventivo";
        }

        // Restituisce la pagina di ringraziamento del preventivo
        @GetMapping("/inviato")
        public String preventivoInviato(Authentication authentication) {

            // Controllo autenticazione per Redirect RBAC
            if (authentication != null && authentication.isAuthenticated() 
                    && !(authentication instanceof AnonymousAuthenticationToken)) {
                
                if (authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_UTENTE"))) {
                    return "redirect:/client";
                }
                
                if (authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    return "redirect:/admin";
                }
            }

            return "preventivo-success";
        }


        // Gestisce l'invio del modulo preventivo
        @PostMapping
        public String submitPreventivo(
                @Valid @ModelAttribute("preventivoRequest") PreventivoRequestDto preventivoRequest,
                BindingResult bindingResult,
                Model model,
                RedirectAttributes redirectAttributes) {

            if (bindingResult.hasErrors()) {
                // Ricarica le opzioni dei servizi per non lasciare la select vuota
                model.addAttribute("serviziOptions", servizioService.readAllAttiviOptions());
                model.addAttribute("errorMessage", "Per favore, compila correttamente tutti i campi obbligatori.");
                return "preventivo"; // Ritorna alla form mostrando gli errori
            }

            try {
                preventivoService.createGuestRequest(preventivoRequest);
                redirectAttributes.addFlashAttribute("successMessage", "Richiesta di preventivo inviata correttamente.");
            } catch (RuntimeException exception) {
                log.error("Errore durante la creazione del preventivo guest", exception);
                model.addAttribute("serviziOptions", servizioService.readAllAttiviOptions());
                model.addAttribute("errorMessage", "Impossibile inviare la richiesta di preventivo. Riprova più tardi.");
                return "preventivo";
            }

            return "redirect:/preventivo/inviato";
        }

}