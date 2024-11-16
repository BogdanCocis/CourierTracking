package com.utcn.Backend.repository;

import com.utcn.Backend.entity.Courier;
import com.utcn.Backend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SessionIdRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByCourier(Courier courier);
}
