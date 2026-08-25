package com.generation.giardini.controller.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.giardini.dto.RegistrationDTO;
import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.repository.UtenteRepository;
import com.generation.giardini.security.CustomUserDetailsService;

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

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
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
        
        String emailClean = form.email() != null ? form.email().trim().toLowerCase() : "";

        // 1. Controllo se l'email esiste già a DB
        if (!emailClean.isEmpty() && utenteRepository.findByEmailIgnoreCase(emailClean).isPresent()) {
            bindingResult.rejectValue("email", "error.registrazione", "Questa email è già registrata.");
        }

        // 2. Controllo coincidenza password
        if (form.password() != null && !form.password().equals(form.passwordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.registrazione", "Le password non coincidono.");
        }

        // 3. Se ci sono errori di validazione (dal DTO o dai controlli manuali), ricarica la pagina
        if (bindingResult.hasErrors()) {
            return "registrazione";
        }

        // 4. Creazione nuovo Utente
        Utente utente = new Utente();
        utente.setNome(form.nome() != null ? form.nome().trim() : "");
        utente.setCognome(form.cognome() != null ? form.cognome().trim() : "");
        utente.setEmail(emailClean);
        utente.setTelefono(form.telefono() != null ? form.telefono().trim() : null);
        utente.setPassword(passwordEncoder.encode(form.password()));
        utente.setAttivo(true);
        utente.setGuest(false);

        utenteRepository.save(utente);

        // 5. Auto-Login automatico post-registrazione
        UserDetails userDetails = userDetailsService.loadUserByUsername(utente.getEmail());
        UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/client";
    }
}