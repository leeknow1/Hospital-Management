package com.example.myhospital.repository;

import com.example.myhospital.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long> {
    Page<ScheduleEntity> findByNameDoctor(String nameDoctor, Pageable pageable);
    Page<ScheduleEntity> findByNamePatient(String namePatient, Pageable pageable);
    Page<ScheduleEntity> findByNameDoctorAndNamePatient(String nameDoctor, String namePatient, Pageable pageable);
}
