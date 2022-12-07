package com.example.myhospital.repository;

import com.example.myhospital.entity.MedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineEntityRepository extends JpaRepository<MedicineEntity, Long> {
    Page<MedicineEntity> findByName(String name, Pageable pageable);
    MedicineEntity findById(long id);
}