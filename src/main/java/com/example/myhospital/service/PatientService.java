package com.example.myhospital.service;

import com.example.myhospital.entity.MedicineEntity;
import com.example.myhospital.entity.PatientEntity;
import com.example.myhospital.repository.MedicineEntityRepository;
import com.example.myhospital.repository.PatientsEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.List;

import static com.example.myhospital.controller.ControllerUtils.loggedDate;
import static com.example.myhospital.service.HospitalServiceUtils.*;

@Service
public class PatientService {
    private final PatientsEntityRepository patientsEntityRepository;
    private final MedicineEntityRepository medicineEntityRepository;
    private String message = "";

    public PatientService(PatientsEntityRepository patientsEntityRepository, MedicineEntityRepository medicineEntityRepository) {
        this.patientsEntityRepository = patientsEntityRepository;
        this.medicineEntityRepository = medicineEntityRepository;
    }

    public String getPatientPage(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name
            , @RequestParam(defaultValue = "1", required = false, value = "page") int page)
    {
        Page<PatientEntity> patients;
        Pageable pageable = getPages(page, 10, "id");

        if(!name.isBlank()){
            patients = patientsEntityRepository.findByName(name, pageable);
        }
        else {
            patients = patientsEntityRepository.findAll(pageable);
        }
        return getEverything(patients, page, model, "patient", "patient");
    }

    public String getAddPatient(@ModelAttribute("patient") PatientEntity patient, Model model){
        model.addAttribute("LoggedDate", loggedDate);
        List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
        sortingMedicine(medicineEntities);
        model.addAttribute("allMed", medicineEntities);
        model.addAttribute("message", message);
        return "patientAdd";
    }

    public String addPatient(@ModelAttribute("patient") @Valid PatientEntity patient, BindingResult bindingResult, Model model){
        MedicineEntity med = medicineEntityRepository.findById(patient.getIdMedicine());
        if(med.getStocked()==0){
            message = "Medicine is out of stock!";
            model.addAttribute("LoggedDate", loggedDate);
            List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
            sortingMedicine(medicineEntities);
            model.addAttribute("allMed", medicineEntities);
            model.addAttribute("message", message);
            return "patientAdd";
        }else{
            med.setStocked(med.getStocked()-1);
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            return "patientAdd";
        }

        message = "";
        patientsEntityRepository.save(patient);
        return "redirect:/patient";
    }

    public String getEditPatient(long id, Model model){
        PatientEntity patient = patientsEntityRepository.findById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("LoggedDate", loggedDate);

        List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
        sortingMedicine(medicineEntities);
        model.addAttribute("allMed", medicineEntities);
        model.addAttribute("message", message);
        return "patientEdit";
    }

    public String postEditPatient(long id, @ModelAttribute("medicine") @Valid PatientEntity patientEntity, BindingResult bindingResult, Model model){
        MedicineEntity med = medicineEntityRepository.findById(patientEntity.getIdMedicine());
        if(med.getStocked()==0){
            message = "Medicine is out of stock!";
            PatientEntity patient = patientsEntityRepository.findById(id);
            model.addAttribute("patient", patient);
            model.addAttribute("LoggedDate", loggedDate);

            List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
            sortingMedicine(medicineEntities);
            model.addAttribute("allMed", medicineEntities);
            model.addAttribute("message", message);
            return "patientEdit";
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            return "patientEdit";
        }

        PatientEntity patient = patientsEntityRepository.findById(id);
        patient.setName(patientEntity.getName());
        patient.setSex(patientEntity.getSex());
        patient.setPhone(patientEntity.getPhone());
        patient.setBirthday(patientEntity.getBirthday());
        if(patient.getIdMedicine()!=patientEntity.getIdMedicine()){
            med.setStocked(med.getStocked()-1);
        }
        patient.setIdMedicine(patientEntity.getIdMedicine());
        patientsEntityRepository.save(patient);
        message = "";
        return "redirect:/patient";
    }

    public String deletePatient(long id){
        patientsEntityRepository.deleteById(id);
        return "redirect:/patient";
    }
}
