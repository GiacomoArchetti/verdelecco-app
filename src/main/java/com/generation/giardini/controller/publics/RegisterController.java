package com.generation.giardini.controller.publics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {


    //METODI GET

        //Restituisce la pagina di registrazione
        @GetMapping("")
        public String registrazione() {
            return "registrazione";
        }


        
    //METODI POST

        //Gestisce l'invio del modulo di registrazione
        @PostMapping("")
        public String submitRegistrazione() {
            return "redirect:/";
        }

}