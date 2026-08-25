package com.generation.giardini.controller.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.giardini.dto.RegistrationDTO;
import com.generation.giardini.security.CustomUserDetailsService;
import com.generation.giardini.service.utente.UtenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final UtenteService utenteService;
    private final CustomUserDetailsService userDetailsService;

    // --- METODI GET ---

    @GetMapping("")
    public String registrazione(Model model, Authentication authentication) {

        // Controllo RBAC: Se già autenticato, redirect alla propria dashboard
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

        if (!model.containsAttribute("registrazione")) {
            model.addAttribute("registrazione", new RegistrationDTO("", "", "", "", "", ""));
        }
        return "registrazione";
    }

    // --- METODI POST ---

    @PostMapping("")
    public String submitRegistrazione(
            @Valid @ModelAttribute("registrazione") RegistrationDTO form,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {

        // 1. Controllo coincidenza password a livello di form (prima di chiamare il service)
        if (form.password() != null && !form.password().equals(form.passwordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.registrazione", "Le password non coincidono.");
        }

        // 2. Se ci sono errori strutturali o di password, ricarica la pagina
        if (bindingResult.hasErrors()) {
            return "registrazione";
        }

        try {
            // 3. Delega totale al service per la registrazione e validazione email esistente
            utenteService.register(form);

            // 4. Auto-Login automatico post-registrazione
            UserDetails userDetails = userDetailsService.loadUserByUsername(form.email().trim().toLowerCase());
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authToken);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        } catch (RuntimeException exception) {
            log.error("Errore durante la registrazione dell'utente", exception);
            // Intercettiamo l'eccezione (es. email già esistente lanciata dal service) e la mostriamo nel form
            bindingResult.rejectValue("email", "error.registrazione", exception.getMessage());
            return "registrazione";
        }

        return "redirect:/client";
    }
}