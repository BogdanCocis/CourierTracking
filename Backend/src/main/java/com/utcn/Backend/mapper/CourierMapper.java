package com.utcn.Backend.mapper;

import com.utcn.Backend.dto.RegisterDTO;
import com.utcn.Backend.entity.Courier;


public class CourierMapper {
    public static Courier toCourier(RegisterDTO registerDto) {
        return Courier.builder()
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .courierRole(registerDto.getCourierRole())
                .build();
    }
}
