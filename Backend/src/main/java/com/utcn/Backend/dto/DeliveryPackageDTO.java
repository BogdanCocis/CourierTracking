package com.utcn.Backend.dto;

import com.utcn.Backend.entity.DeliveryPackageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DeliveryPackageDTO {
    private UUID courierId;
    private Integer idPackage;
    private Date createdOn;
    private String deliveryAddress;
    private boolean payOnDelivery;
    private DeliveryPackageStatus deliveryPackageStatus;
    private String clientEmail;
    private String courierName;
}