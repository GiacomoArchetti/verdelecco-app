package com.generation.giardini.controller.publics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController1 {

    //Restituisce la pagina della home
    @GetMapping
    public String home() {
        return "home";
    }

}