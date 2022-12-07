package com.example.myhospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.myhospital.controller.ControllerUtils.loggedDate;

@Controller
public class MainController {

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("LoggedDate", loggedDate);
        return "home";
    }
}