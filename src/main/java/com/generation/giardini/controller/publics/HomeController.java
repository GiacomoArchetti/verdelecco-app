package com.generation.giardini.controller.publics;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.giardini.service.recensione.RecensioneService;
import com.generation.giardini.service.servizio.ServizioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ServizioService servizioService;
    private final RecensioneService recensioneService;

    // Restituisce la pagina della home
    @GetMapping
    public String home(Model model) {
        try {
            // Delega totalmente al service il recupero delle opzioni dettagliate
            model.addAttribute("serviziOptions", servizioService.readDetailedServizioOptions());
            model.addAttribute("recensioni", recensioneService.readAll());

        } catch (RuntimeException exception) {
            log.error("Errore durante il caricamento della pagina Home", exception);
            model.addAttribute("errorMessage", "Impossibile caricare correttamente la pagina. Riprova più tardi.");
        }

        return "home";
    }
}