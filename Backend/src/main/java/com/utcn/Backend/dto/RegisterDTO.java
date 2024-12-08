package com.utcn.Backend.dto;

import com.utcn.Backend.entity.CourierRole;
import lombok.Data;

@Data

public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private CourierRole courierRole;
    private String managerId;
}
