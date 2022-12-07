package com.example.myhospital.repository;

import com.example.myhospital.entity.GuardianEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianEntityRepository extends JpaRepository<GuardianEntity, Long> {
    GuardianEntity findById(long id);
    Page<GuardianEntity> findByName(String name, Pageable pageable);
}
