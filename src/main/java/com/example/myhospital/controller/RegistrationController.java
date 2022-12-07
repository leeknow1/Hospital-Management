package com.example.myhospital.controller;

import com.example.myhospital.entity.UsersEntity;
import com.example.myhospital.repository.UsersEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.example.myhospital.controller.ControllerUtils.loggedDate;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UsersEntityRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UsersEntityRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegistration(@ModelAttribute("newUser")UsersEntity usersEntity, Model model){
        model.addAttribute("LoggedDate", loggedDate);
        return "registration";
    }

    @PostMapping
    public String postRegistration(@Valid @ModelAttribute("newUser")UsersEntity usersEntity, BindingResult bindingResult, Model model){
        if(usersRepository.findByEmail(usersEntity.getEmail()) != null ){
            model.addAttribute("message", "User already exist!");
            return "registration";
        }

        if(bindingResult.hasErrors()){
            return "registration";
        }

        usersEntity.setPassword(this.passwordEncoder.encode(usersEntity.getPassword()));
        usersRepository.save(usersEntity);
        return "redirect:/";
    }
}
