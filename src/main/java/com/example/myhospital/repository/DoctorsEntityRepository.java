package com.example.myhospital.repository;

import com.example.myhospital.entity.DoctorsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsEntityRepository extends JpaRepository<DoctorsEntity, Long> {
    Page<DoctorsEntity> findByName(String name, Pageable pageable);
    Page<DoctorsEntity> findByType(String type, Pageable pageable);
    Page<DoctorsEntity> findByNameAndType(String name, String type, Pageable pageable);
    DoctorsEntity findById(long id);
}