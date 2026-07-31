package com.generation.giardini.controller;

import com.generation.giardini.dto.PreventivoRequestDto;
import com.generation.giardini.repository.PrenotazioneRepository;
import com.generation.giardini.repository.PreventivoRepository;
import com.generation.giardini.repository.RecensioneRepository;
import com.generation.giardini.repository.UtenteRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UtenteRepository utenteRepository;
    private final PreventivoRepository preventivoRepository;
    private final RecensioneRepository recensioneRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    public HomeController(UtenteRepository utenteRepository,
                          PreventivoRepository preventivoRepository,
                          RecensioneRepository recensioneRepository,
                          PrenotazioneRepository prenotazioneRepository) {
        this.utenteRepository = utenteRepository;
        this.preventivoRepository = preventivoRepository;
        this.recensioneRepository = recensioneRepository;
        this.prenotazioneRepository = prenotazioneRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/preventivo")
    public String preventivo(Model model) {
        if (!model.containsAttribute("preventivoRequest")) {
            model.addAttribute("preventivoRequest", new PreventivoRequestDto());
        }
        return "preventivo";
    }

    @PostMapping("/preventivo")
    public String submitPreventivo(@ModelAttribute PreventivoRequestDto preventivoRequest) {
        return "redirect:/preventivo/inviato";
    }

    @GetMapping("/preventivo/inviato")
    public String preventivoInviato() {
        return "preventivo-success";
    }

    @GetMapping("/registrazione")
    public String registrazione() {
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String submitRegistrazione() {
        return "redirect:/";
    }

    @GetMapping("/accedi")
    public String accedi() {
        return "login";
    }

    @PostMapping("/accedi")
    public String submitAccedi() {
        return "redirect:/utente";
    }

    @GetMapping("/utente")
    public String utente() {
        return "utente";
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                        @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                        @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                        @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage) {
        int pageSize = 5;
        model.addAttribute("clienti", utenteRepository.findAll(PageRequest.of(clientiPage, pageSize)));
        model.addAttribute("preventivi", preventivoRepository.findAll(PageRequest.of(preventiviPage, pageSize)));
        model.addAttribute("prenotazioni", prenotazioneRepository.findAll(PageRequest.of(prenotazioniPage, pageSize)));
        model.addAttribute("recensioni", recensioneRepository.findAll(PageRequest.of(recensioniPage, pageSize)));
        return "admin";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
