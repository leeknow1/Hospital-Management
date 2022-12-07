package com.example.myhospital.repository;

import com.example.myhospital.entity.RoomsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsEntityRepository extends JpaRepository<RoomsEntity, Long> {
    List<RoomsEntity> findByRoomType(String type);
    Page<RoomsEntity> findByRoomType(String type, Pageable pageable);
    RoomsEntity findByRoomNumber(long roomNumber);
}
