package com.example.myhospital.repository;

import com.example.myhospital.entity.OutpatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutpatientEntityRepository extends JpaRepository<OutpatientEntity, Long> {
    OutpatientEntity findById(long id);
    Page<OutpatientEntity> findByName(String name, Pageable pageable);
    Page<OutpatientEntity> findByNameMedicine(String nameMedicine, Pageable pageable);
    Page<OutpatientEntity> findByNameAndNameMedicine(String name, String nameMedicine, Pageable pageable);
}
