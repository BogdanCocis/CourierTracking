package com.utcn.Backend.mapper;

import com.utcn.Backend.dto.RegisterDTO;
import com.utcn.Backend.entity.Courier;


public class CourierMapper {
    public static Courier toCourier(RegisterDTO registerDTO) {
        return Courier.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .courierRole(registerDTO.getCourierRole())
                .build();
    }
}
