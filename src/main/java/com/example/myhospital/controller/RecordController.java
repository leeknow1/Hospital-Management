package com.example.myhospital.controller;

import com.example.myhospital.entity.GuardianEntity;
import com.example.myhospital.entity.InpatientEntity;
import com.example.myhospital.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/doctor/rooms")
    public String getRooms(Model model, @RequestParam(required = false, defaultValue = "", value = "roomType") String roomType,
                           @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return recordService.getRooms(model, roomType, page);
    }

    @GetMapping("/patient/treatment")
    public String getTreatment(Model model,  @RequestParam(required = false, defaultValue = "", value = "doctorName") String doctorName,
                               @RequestParam(required = false, defaultValue = "", value = "patientName") String patientName,
                               @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return recordService.getTreatment(model, doctorName, patientName, page);
    }

    @GetMapping("/patient/treatment/add")
    public String getAddTreatment(@ModelAttribute("treatment") InpatientEntity inpatientEntity, Model model){
        return recordService.getAddTreatment(inpatientEntity, model);
    }

    @PostMapping("/patient/treatment/add")
    public String postAddTreatment(@ModelAttribute("treatment") @Valid InpatientEntity inpatientEntity, BindingResult bindingResult, Model model){
        return recordService.addTreatment(inpatientEntity, bindingResult, model);
    }

    @GetMapping("/patient/treatment/edit/{id}")
    public String getEditTreatment(@PathVariable long id, Model model){
        return recordService.getEditTreatment(id, model);
    }

    @PostMapping("/patient/treatment/edit/{id}")
    public String postEditTreatment(@PathVariable long id, @ModelAttribute("treatment") @Valid InpatientEntity inpatientEntity, BindingResult bindingResult, Model model){
        return recordService.postEditTreatment(id, inpatientEntity, bindingResult, model);
    }

    @GetMapping("/patient/treatment/delete/{id}")
    public String deleteTreatment(@PathVariable long id){
        return recordService.deleteTreatment(id);
    }

    @GetMapping("/patient/guardian")
    public String getGuardian(Model model, @RequestParam(required = false, defaultValue = "", value = "name") String name,
                              @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return recordService.getGuardian(model, name, page);
    }

    @GetMapping("/patient/guardian/add")
    public String getAddGuardian(@ModelAttribute("guardian")GuardianEntity guardianEntity, Model model){
        return recordService.getAddGuardian(guardianEntity, model);
    }

    @PostMapping("/patient/guardian/add")
    public String postAddGuardian(@ModelAttribute("guardian") @Valid GuardianEntity guardianEntity, BindingResult bindingResult, Model model){
        return recordService.postAddGuardian(guardianEntity, bindingResult, model);
    }

    @GetMapping("/patient/guardian/edit/{id}")
    public String getEditGuardian(@PathVariable long id, Model model){
        return recordService.getEditGuardian(id, model);
    }

    @PostMapping("/patient/guardian/edit/{id}")
    public String postEditGuardian(@PathVariable long id, @ModelAttribute("guardian") @Valid GuardianEntity guardianEntity, BindingResult bindingResult, Model model){
        return recordService.postEditGuardian(id, guardianEntity, bindingResult, model);
    }

    @GetMapping("/patient/guardian/delete/{id}")
    public String deleteGuardian(@PathVariable long id){
        return  recordService.deleteGuardian(id);
    }
}
