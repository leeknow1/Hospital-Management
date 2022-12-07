package com.example.myhospital.service;


import com.example.myhospital.entity.DoctorsEntity;
import com.example.myhospital.entity.PatientEntity;
import com.example.myhospital.entity.RoomsEntity;
import com.example.myhospital.entity.ScheduleEntity;
import com.example.myhospital.repository.DoctorsEntityRepository;
import com.example.myhospital.repository.PatientsEntityRepository;
import com.example.myhospital.repository.RoomsEntityRepository;
import com.example.myhospital.repository.ScheduleEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class DoctorService {

    private final DoctorsEntityRepository doctorsEntityRepository;
    private final ScheduleEntityRepository scheduleEntityRepository;
    private final PatientsEntityRepository patientsEntityRepository;
    private final RoomsEntityRepository roomsEntityRepository;

    public DoctorService(DoctorsEntityRepository doctorsEntityRepository, ScheduleEntityRepository scheduleEntityRepository, PatientsEntityRepository patientsEntityRepository, RoomsEntityRepository roomsEntityRepository) {
        this.doctorsEntityRepository = doctorsEntityRepository;
        this.scheduleEntityRepository = scheduleEntityRepository;
        this.patientsEntityRepository = patientsEntityRepository;
        this.roomsEntityRepository = roomsEntityRepository;
    }

    public String getDoctorPage(Model model, @RequestParam(defaultValue = "", required = false, value = "name") String name,
                                @RequestParam(required = false, defaultValue = "", value = "type") String type,
                                @RequestParam(defaultValue = "1", required = false, value = "page") int page)
    {
        Page<DoctorsEntity> doctors;
        Pageable pageable = getPages(page, 10, "id");

        if(!name.isBlank() && type.isBlank()){
            doctors = doctorsEntityRepository.findByName(name, pageable);
        }
        else if(name.isBlank() && !type.isBlank()){
            doctors = doctorsEntityRepository.findByType(type, pageable);
        }
        else if(!name.isBlank() && !type.isBlank()){
            doctors = doctorsEntityRepository.findByNameAndType(name, type, pageable);
        }
        else {
            doctors = doctorsEntityRepository.findAll(pageable);
        }

        return getEverything(doctors, page, model, "doctor", "doctor");
    }

    public String addDoctor(@ModelAttribute("doctor") @Valid DoctorsEntity doctorsEntity, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            return "doctorAdd";
        }

        doctorsEntityRepository.save(doctorsEntity);
        return "redirect:/doctor";
    }

    public String getEditDoctor(long id, Model model) {
        DoctorsEntity doctorsEntity = doctorsEntityRepository.findById(id);
        model.addAttribute("doctor", doctorsEntity);
        model.addAttribute("LoggedDate", loggedDate);
        return "doctorEdit";
    }

    public String postEditDoctor(long id, @ModelAttribute("doctor") @Valid DoctorsEntity doctorsEntity,
                                 BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("LoggedDate", loggedDate);
            return "medicineEdit";
        }
        DoctorsEntity entity = doctorsEntityRepository.findById(id);
        entity.setName(doctorsEntity.getName());
        entity.setSex(doctorsEntity.getSex());
        entity.setType(doctorsEntity.getType());
        entity.setPhone(doctorsEntity.getPhone());
        entity.setBirthday(doctorsEntity.getBirthday());
        entity.setCharge(doctorsEntity.getCharge());
        doctorsEntityRepository.save(entity);

        return "redirect:/doctor";
    }

    public String deleteDoctor(long id) {
        doctorsEntityRepository.deleteById(id);
        return "redirect:/doctor";
    }

    public String getSchedule(Model model, @RequestParam(required = false, defaultValue = "", value = "nameDoctor") String nameDoctor,
                              @RequestParam(required = false, defaultValue = "", value = "namePatient") String namePatient,
                                @RequestParam(defaultValue = "1", required = false, value = "page") int page)
    {
        Page<ScheduleEntity> schedule;
        Pageable pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "date", "time"));

        if(!nameDoctor.isBlank() && namePatient.isBlank()){
            schedule = scheduleEntityRepository.findByNameDoctor(nameDoctor, pageable);
        } else if(nameDoctor.isBlank() && !namePatient.isBlank()){
            schedule = scheduleEntityRepository.findByNamePatient(namePatient, pageable);
        } else if(!nameDoctor.isBlank() && !namePatient.isBlank()){
            schedule = scheduleEntityRepository.findByNameDoctorAndNamePatient(nameDoctor, namePatient, pageable);
        } else {
            schedule = scheduleEntityRepository.findAll(pageable);
        }

        return getEverything(schedule, page, model, "doctor/appointment", "schedule");
    }

    public String postMakeAppointment(@ModelAttribute("appointment") @Valid ScheduleEntity schedule, BindingResult bindingResult, Model model){
        DoctorsEntity doc = doctorsEntityRepository.findById(schedule.getIdDoctor());
        schedule.setNameDoctor(doc.getName());
        schedule.setTypeDoctor(doc.getType());

        PatientEntity pat = patientsEntityRepository.findById(schedule.getIdPatient());
        schedule.setNamePatient(pat.getName());

        if(bindingResult.hasErrors()){
            getMakeAppointment(schedule, model);
        }

        scheduleEntityRepository.save(schedule);
        return "redirect:/doctor/appointment";
    }

    public String getMakeAppointment(@ModelAttribute("appointment") ScheduleEntity schedule, Model model) {
        List<DoctorsEntity> doctorsEntities = doctorsEntityRepository.findAll();
        sortingDoctors(doctorsEntities);
        model.addAttribute("allDoc", doctorsEntities);

        List<PatientEntity> patientEntities = patientsEntityRepository.findAll();
        sortingPatients(patientEntities);
        model.addAttribute("allPat", patientEntities);

        List<RoomsEntity> roomsEntities = roomsEntityRepository.findByRoomType("Checking");
        sortingRoomsByType(roomsEntities);
        model.addAttribute("allRooms", roomsEntities);

        model.addAttribute("LoggedDate", loggedDate);
        return "makeAppointment";
    }

    public String deleteAppointment(long id) {
        scheduleEntityRepository.deleteById(id);
        return "redirect:/doctor/appointment";
    }
}
