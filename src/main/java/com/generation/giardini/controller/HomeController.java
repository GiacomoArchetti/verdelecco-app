package com.generation.giardini.controller;

import com.generation.giardini.dto.PreventivoRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

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
        return "redirect:/";
    }
}
