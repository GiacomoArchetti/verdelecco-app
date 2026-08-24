package com.generation.giardini.controller.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    //METODI GET

        // Restituisce la pagina di accesso
    @GetMapping
    public String accedi(Authentication authentication) {

        // MODIFICA: Controllo autenticazione per Redirect RBAC
        if (authentication != null && authentication.isAuthenticated() 
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            
            // Se l'utente è loggato come Cliente, viene reindirizzato alla sua area riservata
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_UTENTE"))) {
                return "redirect:/client";
            }
            
            // Se è l'Amministratore, viene reindirizzato alla dashboard admin
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin";
            }
        }
        // FINE MODIFICA

        return "login";
    }

    // METODI POST
    // Note: POST /login gestito da Spring Security filter, NON fare override qui

}
