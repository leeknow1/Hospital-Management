package com.example.myhospital.controller;

import com.example.myhospital.entity.DoctorsEntity;
import com.example.myhospital.entity.ScheduleEntity;
import com.example.myhospital.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.myhospital.controller.ControllerUtils.loggedDate;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String doctorHome(Model model, @RequestParam(required = false, defaultValue = "", value = "name") String name,
                             @RequestParam(required = false, defaultValue = "", value = "type") String type,
                             @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return doctorService.getDoctorPage(model, name, type, page);
    }

    @GetMapping("/add")
    public String getAddDoctor(@ModelAttribute("doctor") DoctorsEntity doctorsEntity, Model model){
        model.addAttribute("LoggedDate", loggedDate);
        return "doctorAdd";
    }

    @PostMapping("/add")
    public String postAddDoctor(@ModelAttribute("doctor") @Valid DoctorsEntity doctorsEntity, BindingResult bindingResult, Model model){
        return doctorService.addDoctor(doctorsEntity, bindingResult, model);
    }

    @GetMapping("/edit/{id}")
    public String getEditDoctor(@PathVariable long id, Model model){
        return doctorService.getEditDoctor(id, model);
    }

    @PostMapping("/edit/{id}")
    public String postEditDoctor(@PathVariable long id, @ModelAttribute("doctor") @Valid DoctorsEntity doctorsEntity,
                                   BindingResult bindingResult, Model model){
        return doctorService.postEditDoctor(id, doctorsEntity, bindingResult, model);
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable long id){
        return doctorService.deleteDoctor(id);
    }

    @GetMapping("/appointment")
    public String getSchedule(Model model, @RequestParam(required = false, defaultValue = "", value = "nameDoctor") String nameDoctor,
                              @RequestParam(required = false, defaultValue = "", value = "namePatient") String namePatient,
                              @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return doctorService.getSchedule(model, nameDoctor, namePatient, page);
    }

    @GetMapping("/make-appointment")
    public String getMakeAppointment(@ModelAttribute("appointment")ScheduleEntity schedule, Model model){
        return doctorService.getMakeAppointment(schedule, model);
    }

    @PostMapping("/make-appointment")
    public String postMakeAppointment(@ModelAttribute("appointment") @Valid ScheduleEntity schedule, BindingResult bindingResult, Model model){
        return doctorService.postMakeAppointment(schedule, bindingResult, model);
    }

    @GetMapping("/appointment/{id}")
    public String deleteAppointment(@PathVariable long id){
        return doctorService.deleteAppointment(id);
    }
}
