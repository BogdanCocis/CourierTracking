package com.utcn.Backend.mapper;

import com.utcn.Backend.dto.CourierDTO;
import com.utcn.Backend.dto.RegisterDTO;
import com.utcn.Backend.entity.Courier;
import org.springframework.stereotype.Component;

@Component

public class CourierMapper {

    public static Courier toCourier(RegisterDTO registerDTO) {
        return Courier.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .courierRole(registerDTO.getCourierRole())
                .build();
    }

    public CourierDTO toCourierDTO(Courier courier) {
        return CourierDTO.builder()
                .idCourier(courier.getIdCourier())
                .name(courier.getName())
                .email(courier.getEmail())
                .build();
    }
}