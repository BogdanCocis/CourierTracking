package com.utcn.proiectScd.repository;

import com.utcn.proiectScd.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {
    Optional<Courier> findByEmail(String email);
    boolean existsByEmail(String email);
}
