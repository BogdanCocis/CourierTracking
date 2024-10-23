package com.utcn.proiectScd.dto;

import com.utcn.proiectScd.entity.Courier;
import com.utcn.proiectScd.util.DeliveryPackageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPackageDTO {
    private Integer idPackage;
    private Courier courier;
    private Date createdOn;
    private String deliveryAddress;
    private boolean payOnDelivery;
    private DeliveryPackageStatus deliveryPackageStatus;
}
