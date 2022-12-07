package com.example.myhospital.repository;

import com.example.myhospital.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsEntityRepository extends JpaRepository<PatientEntity, Long> {
    Page<PatientEntity> findByName(String name, Pageable pageable);
    PatientEntity findById(long id);
}