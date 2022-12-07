package com.example.myhospital.controller;


import com.example.myhospital.entity.MedicineEntity;
import com.example.myhospital.entity.OutpatientEntity;
import com.example.myhospital.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.myhospital.controller.ControllerUtils.loggedDate;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping("")
    public String getMedicinePage(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name
            , @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return medicineService.getMedicinePage(model, name, page);
    }

    @GetMapping("/add")
    public String getAddMedicine(@ModelAttribute("medicine") MedicineEntity medicineEntity, Model model){
        model.addAttribute("LoggedDate", loggedDate);
        return "medicineAdd";
    }

    @PostMapping("/add")
    public String postAddMedicine(@ModelAttribute("medicine") @Valid MedicineEntity medicineEntity, BindingResult bindingResult, Model model){
        return medicineService.addMedicine(medicineEntity, bindingResult, model);
    }

    @GetMapping("/edit/{id}")
    public String getEditMedicine(@PathVariable long id, Model model){
        return medicineService.getEditMedicine(id, model);
    }

    @PostMapping("/edit/{id}")
    public String postEditMedicine(@PathVariable long id, @ModelAttribute("medicine") @Valid MedicineEntity medicineEntity,
                                   BindingResult bindingResult, Model model){
        return medicineService.postEditMedicine(id, medicineEntity, bindingResult, model);
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable long id){
        return medicineService.deleteMedicine(id);
    }

    @GetMapping("/outpatient")
    public String getOutpatients(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name,
                                 @RequestParam(defaultValue = "", required = false, value = "nameMedicine") String nameMedicine
                                ,@RequestParam(defaultValue = "1", required = false, value = "page") int page){
        return medicineService.getOutpatients(model, name, nameMedicine, page);
    }

    @GetMapping("/outpatient/delete/{id}")
    public String deleteOutpatient(@PathVariable long id) {return medicineService.deleteOutpatient(id);}

    @GetMapping("/buy")
    public String buyMedicine( @ModelAttribute("outpatient") OutpatientEntity outpatientEntity, Model model){
        return medicineService.getBuyMedicine(model);
    }

    @PostMapping("/buy")
    public String postBuyMedicine( @ModelAttribute("outpatient") OutpatientEntity outpatientEntity,
                                  BindingResult bindingResult, Model model){
        return medicineService.postBuyMedicine(outpatientEntity, bindingResult, model);
    }
}
