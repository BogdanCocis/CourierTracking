package com.utcn.proiectScd.mapper;

import com.utcn.proiectScd.dto.RegisterDTO;
import com.utcn.proiectScd.entity.Courier;

public class CourierMapper {
    public static Courier toCourier(RegisterDTO registerDTO) {
        return Courier.builder()
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .courierRole(registerDTO.getCourierRole())
                .build();
    }
}
