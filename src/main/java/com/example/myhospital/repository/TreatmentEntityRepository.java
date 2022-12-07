package com.example.myhospital.repository;

import com.example.myhospital.entity.InpatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentEntityRepository extends JpaRepository<InpatientEntity, Long> {
    Page<InpatientEntity> findByDoctorName(String doctorName, Pageable pageable);
    Page<InpatientEntity> findByPatientName(String patientName, Pageable pageable);
    Page<InpatientEntity> findByDoctorNameAndPatientName(String doctorName, String patientName, Pageable pageable);
    InpatientEntity findById(long id);
}
