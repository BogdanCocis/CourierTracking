package com.utcn.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ManagerDeliveredDTO {
    private String managerName;
    private String managerEmail;
    private Long deliveredCount;
}