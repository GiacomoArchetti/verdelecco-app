package com.generation.giardini.controller.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    //METODI GET

        // Gestisce il logout e reindirizza alla home page
        @GetMapping
        public String logout(Authentication authentication) {

            // MODIFICA: Se un utente anonimo tenta di accedere a /logout, lo inoltra direttamente alla Home
            if (authentication == null || !authentication.isAuthenticated() 
                    || authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:/";
            }
            // FINE MODIFICA

            // Se l'utente era autenticato, la sessione viene chiusa e viene riportato alla Home
            return "redirect:/";
        }

}