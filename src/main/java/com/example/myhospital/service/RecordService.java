package com.example.myhospital.service;

import com.example.myhospital.entity.*;
import com.example.myhospital.repository.*;
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
public class RecordService {

    private final RoomsEntityRepository roomsEntityRepository;
    private final TreatmentEntityRepository treatmentEntityRepository;
    private final DoctorsEntityRepository doctorsEntityRepository;
    private final PatientsEntityRepository patientsEntityRepository;
    private final MedicineEntityRepository medicineEntityRepository;

    private final GuardianEntityRepository guardianEntityRepository;
    private String message = "";

    public RecordService(RoomsEntityRepository roomsEntityRepository, TreatmentEntityRepository treatmentEntityRepository, DoctorsEntityRepository doctorsEntityRepository, PatientsEntityRepository patientsEntityRepository, MedicineEntityRepository medicineEntityRepository, GuardianEntityRepository guardianEntityRepository) {
        this.roomsEntityRepository = roomsEntityRepository;
        this.treatmentEntityRepository = treatmentEntityRepository;
        this.doctorsEntityRepository = doctorsEntityRepository;
        this.patientsEntityRepository = patientsEntityRepository;
        this.medicineEntityRepository = medicineEntityRepository;
        this.guardianEntityRepository = guardianEntityRepository;
    }

    public String getRooms(Model model, @RequestParam(required = false, defaultValue = "", value = "roomType") String roomType,
                           @RequestParam(defaultValue = "1", required = false, value = "page") int page) {

        Page<RoomsEntity> rooms;
        Pageable pageable = getPages(page, 12, "id");

        if(!roomType.isBlank()) {
            rooms = roomsEntityRepository.findByRoomType(roomType, pageable);
        } else {
            rooms = roomsEntityRepository.findAll(pageable);
        }

        return getEverything(rooms, page, model, "doctor/rooms", "rooms");
    }

    public String getTreatment(Model model,  @RequestParam(required = false, defaultValue = "", value = "doctorName") String doctorName,
                               @RequestParam(required = false, defaultValue = "", value = "patientName") String patientName,
                               @RequestParam(defaultValue = "1", required = false, value = "page") int page) {

        Page<InpatientEntity> treatmentEntities;
        Pageable pageable = getPages(page, 10, "id");

        if(!doctorName.isBlank() && patientName.isBlank()){
            treatmentEntities = treatmentEntityRepository.findByDoctorName(doctorName, pageable);
        } else if(doctorName.isBlank() && !patientName.isBlank()){
            treatmentEntities = treatmentEntityRepository.findByPatientName(patientName, pageable);
        } else if(!doctorName.isBlank() && !patientName.isBlank()){
            treatmentEntities = treatmentEntityRepository.findByDoctorNameAndPatientName(doctorName, patientName, pageable);
        } else {
            treatmentEntities = treatmentEntityRepository.findAll(pageable);
        }

        return getEverything(treatmentEntities, page, model, "patient/treatment", "treatment");
    }

    public String getAddTreatment(@ModelAttribute("treatment") InpatientEntity inpatientEntity, Model model) {
        return getTreatment("addTreatment", model);
    }

    public String addTreatment(@ModelAttribute("treatment") @Valid InpatientEntity inpatientEntity, BindingResult bindingResult, Model model) {
        RoomsEntity room = roomsEntityRepository.findByRoomNumber(inpatientEntity.getRoomNumber());
        DoctorsEntity doc = doctorsEntityRepository.findById(inpatientEntity.getIdDoctor());
        PatientEntity pat = patientsEntityRepository.findById(inpatientEntity.getIdPatient());
        MedicineEntity med = medicineEntityRepository.findById(inpatientEntity.getIdMedicine());

        inpatientEntity.setRoomNumber(room.getRoomNumber());
        inpatientEntity.setWardType(room.getWardType());
        inpatientEntity.setDoctorName(doc.getName());
        inpatientEntity.setDoctorType(doc.getType());
        inpatientEntity.setPatientName(pat.getName());
        inpatientEntity.setMedicineName(med.getName());
        inpatientEntity.setTotalCharges( (room.getCharge() + doc.getCharge() + med.getPrice()) * inpatientEntity.getDuration() );

        if(med.getStocked()==0){
            message = "Medicine is out of stock!";
            return getTreatment("addTreatment", model);
        } else {
            med.setStocked(med.getStocked()-1);
        }

        if(bindingResult.hasErrors()){
            getAddTreatment(inpatientEntity, model);
        }

        message = "";
        treatmentEntityRepository.save(inpatientEntity);

        return "redirect:/patient/treatment";
    }

    public String getEditTreatment(long id, Model model) {
        InpatientEntity inpatientEntity = treatmentEntityRepository.findById(id);
        model.addAttribute("treatment", inpatientEntity);

        return getTreatment("editTreatment", model);
    }

    public String postEditTreatment(long id, @ModelAttribute("treatment") @Valid InpatientEntity inpatientEntity, BindingResult bindingResult, Model model) {
        RoomsEntity room = roomsEntityRepository.findByRoomNumber(inpatientEntity.getRoomNumber());
        DoctorsEntity doc = doctorsEntityRepository.findById(inpatientEntity.getIdDoctor());
        PatientEntity pat = patientsEntityRepository.findById(inpatientEntity.getIdPatient());
        MedicineEntity med = medicineEntityRepository.findById(inpatientEntity.getIdMedicine());

        inpatientEntity.setWardType(room.getWardType());
        inpatientEntity.setDoctorName(doc.getName());
        inpatientEntity.setDoctorType(doc.getType());
        inpatientEntity.setPatientName(pat.getName());
        inpatientEntity.setMedicineName(med.getName());

        if(med.getStocked()==0){
            message = "Medicine is out of stock!";
            InpatientEntity tre = treatmentEntityRepository.findById(id);
            model.addAttribute("treatment", tre);

            return getTreatment("editTreatment", model);
        }


        if(bindingResult.hasErrors()){
            getAddTreatment(inpatientEntity, model);
        }

        InpatientEntity entity = treatmentEntityRepository.findById(id);
        entity.setRoomNumber(inpatientEntity.getRoomNumber());
        entity.setWardType(inpatientEntity.getWardType());
        entity.setIdDoctor(inpatientEntity.getIdDoctor());
        entity.setDoctorName(inpatientEntity.getDoctorName());
        entity.setDoctorType(inpatientEntity.getDoctorType());
        entity.setIdPatient(inpatientEntity.getIdPatient());
        entity.setPatientName(inpatientEntity.getPatientName());
        if(entity.getIdMedicine()!= inpatientEntity.getIdMedicine()){
            med.setStocked(med.getStocked()-1);
        }
        entity.setIdMedicine(inpatientEntity.getIdMedicine());
        entity.setMedicineName(inpatientEntity.getMedicineName());
        entity.setDuration(inpatientEntity.getDuration());
        entity.setTotalCharges((room.getCharge() + doc.getCharge() + med.getPrice()) * inpatientEntity.getDuration());

        message = "";
        treatmentEntityRepository.save(entity);
        return "redirect:/patient/treatment";
    }

    public String deleteTreatment(long id) {
        treatmentEntityRepository.deleteById(id);
        return "redirect:/patient/treatment";
    }

    private String getTreatment(String view, Model model){

        List<DoctorsEntity> doctorsEntities = doctorsEntityRepository.findAll();
        sortingDoctors(doctorsEntities);
        model.addAttribute("allDoc", doctorsEntities);

        List<PatientEntity> patientEntities = patientsEntityRepository.findAll();
        sortingPatients(patientEntities);
        model.addAttribute("allPat", patientEntities);

        List<RoomsEntity> roomsEntities = roomsEntityRepository.findByRoomType("Treatment");
        sortingRoomsByType(roomsEntities);
        model.addAttribute("allRooms", roomsEntities);

        List<MedicineEntity> medicineEntities = medicineEntityRepository.findAll();
        sortingMedicine(medicineEntities);
        model.addAttribute("allMed", medicineEntities);

        model.addAttribute("LoggedDate", loggedDate);
        model.addAttribute("message", message);

        return view;
    }

    public String getGuardian(Model model, String name, int page) {
        Page<GuardianEntity> guardians;
        Pageable pageable = getPages(page, 10, "id");

        if(name.isBlank()){
            guardians = guardianEntityRepository.findAll(pageable);
        } else {
            guardians = guardianEntityRepository.findByName(name, pageable);
        }

        return getEverything(guardians, page, model, "guardian", "guardian");
    }

    public String getAddGuardian(@ModelAttribute("guardian")GuardianEntity guardianEntity, Model model) {
        model.addAttribute("LoggedDate", loggedDate);
        List<InpatientEntity> inpatientEntities = treatmentEntityRepository.findAll();
        sortingInpatients(inpatientEntities);
        model.addAttribute("inpatients", inpatientEntities);

        return "guardianAdd";
    }


    public String postAddGuardian(@ModelAttribute("guardian") @Valid GuardianEntity guardianEntity, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            List<InpatientEntity> inpatientEntities = treatmentEntityRepository.findAll();
            sortingInpatients(inpatientEntities);
            model.addAttribute("inpatients", inpatientEntities);
            return "guardianAdd";
        }

        guardianEntityRepository.save(guardianEntity);
        return "redirect:/patient/guardian";
    }


    public String getEditGuardian(long id, Model model) {
        GuardianEntity guardianEntity = guardianEntityRepository.findById(id);
        model.addAttribute("guardian", guardianEntity);
        model.addAttribute("LoggedDate", loggedDate);
        List<InpatientEntity> inpatientEntities = treatmentEntityRepository.findAll();
        sortingInpatients(inpatientEntities);
        model.addAttribute("inpatients", inpatientEntities);
        return "guardianEdit";
    }

    public String postEditGuardian(long id, @ModelAttribute("guardian") @Valid GuardianEntity guardianEntity, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            List<InpatientEntity> inpatientEntities = treatmentEntityRepository.findAll();
            sortingInpatients(inpatientEntities);
            model.addAttribute("inpatients", inpatientEntities);
            return "guardianEdit";
        }

        GuardianEntity entity = guardianEntityRepository.findById(id);
        entity.setName(guardianEntity.getName());
        entity.setPhone(guardianEntity.getPhone());
        entity.setSex(guardianEntity.getSex());
        entity.setRelationship(guardianEntity.getRelationship());
        entity.setInpatientId(guardianEntity.getInpatientId());

        guardianEntityRepository.save(entity);
        return "redirect:/patient/guardian";
    }

    public String deleteGuardian(long id) {
        guardianEntityRepository.deleteById(id);
        return "redirect:/patient/guardian";
    }
}
