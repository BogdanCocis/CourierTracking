package com.utcn.Backend.service;

import com.utcn.Backend.dto.CourierDTO;
import com.utcn.Backend.dto.ManagerDeliveredDTO;
import com.utcn.Backend.dto.RegisterDTO;
import com.utcn.Backend.entity.Courier;
import com.utcn.Backend.entity.CourierRole;
import com.utcn.Backend.entity.Session;
import com.utcn.Backend.exception.DuplicateException;
import com.utcn.Backend.exception.InvalidDataException;
import com.utcn.Backend.mapper.CourierMapper;
import com.utcn.Backend.repository.CourierRepository;
import com.utcn.Backend.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final SessionIdService sessionIdService;
    private final PasswordEncoder passwordEncoder;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CourierMapper courierMapper;

    public Session login(String loginEmail, String loginPassword) throws Exception {


        Optional<Courier> courier = courierRepository.findByEmail(loginEmail);
        if (courier.isPresent()) {
            Session session;
            String userPassword = courier.get().getPassword();

            if (bCryptPasswordEncoder.matches(loginPassword, userPassword)) {

                Optional<Session> existingSessionId = sessionIdService.findByUserId(courier.get());
                if (existingSessionId.isPresent()) {
                    if (existingSessionId.get().getExpirationTime().isAfter(LocalDateTime.now())) {
                        return existingSessionId.get();
                    } else {
                        sessionIdService.delete(existingSessionId.get());
                        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(360);
                        session = new Session(UUID.randomUUID(), expirationDate, courier.get());
                        sessionIdService.save(session);
                        return session;
                    }
                } else {
                    LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(360);
                    session = new Session(UUID.randomUUID(), expirationDate, courier.get());
                    sessionIdService.save(session);
                    return session;
                }
            } else {
                throw new Exception("Invalid password!");
            }
        }
        throw new Exception("User not found!");
    }


    public void saveUser(RegisterDTO registerDTO) throws InvalidDataException, DuplicateException {
        UserValidator.validateRegister(registerDTO);

        if (courierRepository.existsByEmail(registerDTO.getEmail())) {
            throw new DuplicateException("Email is already in use!");
        }

        Courier defaultManager = null;
        if (registerDTO.getCourierRole() == null || registerDTO.getCourierRole() == CourierRole.COURIER) {
            if (registerDTO.getManagerId() == null || registerDTO.getManagerId().isEmpty()) {
                defaultManager = courierRepository.findByEmail("cocis.bogdan@yahoo.com")
                        .orElseThrow(() -> new InvalidDataException("Default manager not found!"));
            } else {
                defaultManager = courierRepository.findById(UUID.fromString(registerDTO.getManagerId()))
                        .orElseThrow(() -> new InvalidDataException("Invalid manager ID!"));
            }
        }

        Courier courier = CourierMapper.toCourier(registerDTO);

        if (registerDTO.getCourierRole() == null) {
            courier.setCourierRole(CourierRole.COURIER);
        }

        courier.setManager(defaultManager);

        courier.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        courierRepository.save(courier);
    }


    public List<CourierDTO> getCouriersWithoutPendingPackages() {
        return courierRepository.getAllCouriersWithoutPendingPackages()
                .stream()
                .map(courierMapper::toCourierDTO)
                .collect(Collectors.toList());
    }

    public List<ManagerDeliveredDTO> getAllManagersAndDeliveredNumber() {
        return courierRepository.getAllManagersAndDeliveredNumber()
                .stream()
                .map(result -> ManagerDeliveredDTO.builder()
                        .managerName((String) result[0])
                        .managerEmail((String) result[1])
                        .deliveredCount((Long) result[2])
                        .build())
                .collect(Collectors.toList());
    }

}


