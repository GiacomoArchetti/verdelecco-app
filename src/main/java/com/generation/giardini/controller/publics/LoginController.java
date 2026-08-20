package com.generation.giardini.controller.publics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    //METODI GET

        //Restituisce la pagina di accesso
        @GetMapping
        public String accedi() {
            return "login";
        }



    //METODI POST
    // Note: POST /login handled by Spring Security filter, don't override here

}
