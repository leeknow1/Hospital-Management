package com.example.myhospital.repository;

import com.example.myhospital.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersEntityRepository extends JpaRepository<UsersEntity, Long> {
    UsersEntity findByUsername(String username);
    UsersEntity findByEmail(String email);
}
