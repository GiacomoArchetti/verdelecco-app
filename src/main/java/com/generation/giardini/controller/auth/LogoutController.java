package com.generation.giardini.controller.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    //METODI GET

        // Gestisce il logout e restituisce la pagina della home
        @GetMapping
        public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

            // MODIFICA: Controllo RBAC - Se l'utente è anonimo, lo inoltra direttamente alla Home
            if (authentication == null || !authentication.isAuthenticated() 
                    || authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:/";
            }

            // Se l'utente è loggato (Cliente o Admin), distrugge la sessione e pulisce l'autenticazione
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            // FINE MODIFICA

            return "redirect:/";
        }
}