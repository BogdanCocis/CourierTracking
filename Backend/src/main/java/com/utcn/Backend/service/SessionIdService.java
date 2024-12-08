package com.utcn.Backend.service;

import com.utcn.Backend.entity.Courier;
import com.utcn.Backend.entity.Session;
import com.utcn.Backend.repository.SessionIdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SessionIdService {
    private SessionIdRepository sessionIdRepository;

    public void save(Session session) {
        sessionIdRepository.save(session);
    }

    public Optional<Session> findByUserId(Courier courier) {
        return sessionIdRepository.findByCourier(courier);
    }

    public void delete(Session session) {
        sessionIdRepository.delete(session);
    }

    public Optional<Session> findBySessionId(UUID sessionId) {
        return sessionIdRepository.findById(sessionId);
    }
}
