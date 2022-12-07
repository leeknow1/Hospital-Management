package com.example.myhospital.service;


import com.example.myhospital.entity.MedicineEntity;
import com.example.myhospital.entity.OutpatientEntity;
import com.example.myhospital.repository.MedicineEntityRepository;
import com.example.myhospital.repository.OutpatientEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.myhospital.controller.ControllerUtils.loggedDate;
import static com.example.myhospital.service.HospitalServiceUtils.*;

@Service
public class MedicineService {

    private final MedicineEntityRepository medicineEntityRepository;
    private final OutpatientEntityRepository outpatientEntityRepository;
    private String message = "";

    public MedicineService(MedicineEntityRepository medicineEntityRepository, OutpatientEntityRepository outpatientEntityRepository) {
        this.medicineEntityRepository = medicineEntityRepository;
        this.outpatientEntityRepository = outpatientEntityRepository;
    }

    public String getMedicinePage(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name
            , @RequestParam(defaultValue = "1", required = false, value = "page") int page)
    {
        Page<MedicineEntity> medicine;
        Pageable pageable = getPages(page, 6, "id");

        if(!name.isBlank()){
            medicine = medicineEntityRepository.findByName(name, pageable);
        }
        else {
            medicine = medicineEntityRepository.findAll(pageable);
        }

        return getEverything(medicine, page, model, "medicine", "medicine");
    }

    public String addMedicine(@ModelAttribute("medicine") @Valid MedicineEntity medicineEntity, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            return "medicineAdd";
        }

        medicineEntityRepository.save(medicineEntity);
        return "redirect:/medicine";
    }

    public String getEditMedicine(long id, Model model){
        MedicineEntity medicineEntity = medicineEntityRepository.findById(id);
        model.addAttribute("medicine", medicineEntity);
        model.addAttribute("LoggedDate", loggedDate);
        return "medicineEdit";
    }

    public String postEditMedicine(long id, @ModelAttribute("medicine") @Valid MedicineEntity medicineEntity, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            return "medicineEdit";
        }

        MedicineEntity medicine = medicineEntityRepository.findById(id);
        medicine.setName(medicineEntity.getName());
        medicine.setStocked(medicineEntity.getStocked());
        medicine.setPrice(medicineEntity.getPrice());
        medicineEntityRepository.save(medicine);

        return "redirect:/medicine";
    }

    public String deleteMedicine(long id){
        medicineEntityRepository.deleteById(id);
        return "redirect:/medicine";
    }

    public String getOutpatients(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name,
                                 @RequestParam(defaultValue = "", required = false, value = "nameMedicine") String nameMedicine
            ,@RequestParam(defaultValue = "1", required = false, value = "page") int page) {

        Page<OutpatientEntity> outpatientEntities;
        Pageable pageable = getPages(page, 8, "id");

        if(!name.isBlank() && nameMedicine.isBlank()){
            outpatientEntities = outpatientEntityRepository.findByName(name, pageable);
        } else if(name.isBlank() && !nameMedicine.isBlank()){
            outpatientEntities = outpatientEntityRepository.findByNameMedicine(nameMedicine, pageable);
        } else if(!name.isBlank() && !nameMedicine.isBlank()){
            outpatientEntities = outpatientEntityRepository.findByNameAndNameMedicine(name, nameMedicine, pageable);
        } else {
            outpatientEntities = outpatientEntityRepository.findAll(pageable);
        }

        return getEverything(outpatientEntities, page, model, "medicine/outpatient", "outpatient");
    }

    public String deleteOutpatient(long id) {
        outpatientEntityRepository.deleteById(id);
        return "redirect:/medicine/outpatient";
    }

    public String getBuyMedicine(Model model) {
        List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
        sortingMedicine(medicineEntities);
        model.addAttribute("allMed", medicineEntities);
        model.addAttribute("LoggedDate", loggedDate);
        model.addAttribute("message", message);
        return "outpatientAdd";
    }

    public String postBuyMedicine(@ModelAttribute("outpatient") OutpatientEntity outpatientEntity,
                                  BindingResult bindingResult, Model model) {
        MedicineEntity med = medicineEntityRepository.findById(outpatientEntity.getIdMedicine());
        outpatientEntity.setNameMedicine(med.getName());
        outpatientEntity.setBoughtDate(new java.sql.Date(System.currentTimeMillis()));

        if(med.getStocked()==0){
            message = "Medicine is out of stock!";
            List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
            sortingMedicine(medicineEntities);
            model.addAttribute("allMed", medicineEntities);
            model.addAttribute("LoggedDate", loggedDate);
            model.addAttribute("message", message);
            return "outpatientAdd";
        } else {
            med.setStocked(med.getStocked()-1);
        }

        if(bindingResult.hasErrors()){
            getBuyMedicine(model);
        }

        message = "";
        outpatientEntityRepository.save(outpatientEntity);
        return "redirect:/medicine";
    }
}
