package com.utcn.proiectScd.mapper;

import com.utcn.proiectScd.dto.DeliveryPackageDTO;
import com.utcn.proiectScd.entity.DeliveryPackage;
import org.springframework.stereotype.Component;

@Component
public class DeviveryPackageMapper {

    public DeliveryPackageDTO toPackageDTO(DeliveryPackage deliveryPackage) {
        return DeliveryPackageDTO.builder()
                .idPackage(deliveryPackage.getIdPackage())
                .courier(deliveryPackage.getCourier())
                .createdOn(deliveryPackage.getCreatedOn())
                .deliveryAddress(deliveryPackage.getDeliveryAddress())
                .payOnDelivery(deliveryPackage.isPayOnDelivery())
                .build();
    }

    public DeliveryPackage toDeliveryPackage(DeliveryPackageDTO deliveryPackageDTO) {
        return DeliveryPackage.builder()
                .idPackage(deliveryPackageDTO.getIdPackage())
                .courier(deliveryPackageDTO.getCourier())
                .createdOn(deliveryPackageDTO.getCreatedOn())
                .deliveryAddress(deliveryPackageDTO.getDeliveryAddress())
                .payOnDelivery(deliveryPackageDTO.isPayOnDelivery())
                .build();
    }
}
