package com.utcn.proiectScd.dto;

import com.utcn.proiectScd.util.CourierRole;
import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private CourierRole courierRole;
}
