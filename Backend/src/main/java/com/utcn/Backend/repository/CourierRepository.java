package com.utcn.Backend.repository;

import com.utcn.Backend.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {
    Optional<Courier> findByEmail(String email);
        boolean existsByEmail(String email);
}
