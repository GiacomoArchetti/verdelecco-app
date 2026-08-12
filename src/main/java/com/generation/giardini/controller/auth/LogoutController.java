package com.generation.giardini.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    //METODI GET

        //Gestisce il logout e restituisce la pagina della home
            @GetMapping
            public String logout() {
                return "redirect:/";
            }

}