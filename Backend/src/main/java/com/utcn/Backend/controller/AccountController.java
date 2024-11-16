package com.utcn.Backend.controller;

import com.utcn.Backend.dto.LoginDTO;
import com.utcn.Backend.dto.RegisterDTO;
import com.utcn.Backend.entity.Session;
import com.utcn.Backend.exception.DuplicateException;
import com.utcn.Backend.exception.InvalidDataException;
import com.utcn.Backend.service.CourierService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

@AllArgsConstructor
@RestController
@Slf4j
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(allowCredentials = "true")
@RequestMapping("/api")
public class AccountController {
    private final CourierService courierService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try{
            courierService.saveUser(registerDTO);
        }
        catch(InvalidDataException | DuplicateException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", exception.getMessage()));
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "Account created successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        try{
            Session session = courierService.login(loginDTO.getEmail(), loginDTO.getPassword());

            Cookie sessionCookie = new Cookie("sessionID", session.getSessionId().toString());
            Long cookieAge = Duration.between(LocalDateTime.now(),
                    session.getExpirationTime()).getSeconds();
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            sessionCookie.setMaxAge(cookieAge.intValue());
            response.addCookie(sessionCookie);

            return ResponseEntity.ok(session.getCourier().getCourierRole());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(e.getMessage());
        }
    }
}
