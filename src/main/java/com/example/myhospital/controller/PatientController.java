package com.example.myhospital.controller;

import com.example.myhospital.entity.PatientEntity;
import com.example.myhospital.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("")
    public String getPatientPage(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name
            , @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return patientService.getPatientPage(model, name, page);
    }

    @GetMapping("/add")
    public String getAddPatient(@ModelAttribute("patient") PatientEntity patient, Model model){
        return patientService.getAddPatient(patient, model);
    }

    @PostMapping("/add")
    public String postAddPatient(@ModelAttribute("patient") @Valid PatientEntity patient, BindingResult bindingResult, Model model){
        return patientService.addPatient(patient, bindingResult, model);
    }

    @GetMapping("/edit/{id}")
    public String getEditPatient(@PathVariable long id, Model model){
        return patientService.getEditPatient(id, model);
    }

    @PostMapping("/edit/{id}")
    public String postEditPatient(@PathVariable long id, @ModelAttribute("patient") @Valid PatientEntity patient,
                                   BindingResult bindingResult, Model model){
        return patientService.postEditPatient(id, patient, bindingResult, model);
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable long id){
        return patientService.deletePatient(id);
    }
}
