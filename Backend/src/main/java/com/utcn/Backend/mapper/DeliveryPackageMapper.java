package com.utcn.Backend.mapper;

import com.utcn.Backend.dto.DeliveryPackageDTO;
import com.utcn.Backend.entity.DeliveryPackage;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPackageMapper {
    public DeliveryPackageDTO toPackageDTO(DeliveryPackage deliveryPackage) {
        return DeliveryPackageDTO.builder()
                .idPackage(deliveryPackage.getIdPackage())
                .createdOn(deliveryPackage.getCreatedOn())
                .deliveryAddress(deliveryPackage.getDeliveryAddress())
                .payOnDelivery(deliveryPackage.isPayOnDelivery())
                .deliveryPackageStatus(deliveryPackage.getDeliveryPackageStatus())
                .clientEmail(deliveryPackage.getClientEmail())
                .build();
    }

    public DeliveryPackage toDeliveryPackage(DeliveryPackageDTO deliveryPackageDTO) {
        return DeliveryPackage.builder()
                .idPackage(deliveryPackageDTO.getIdPackage())
                .createdOn(deliveryPackageDTO.getCreatedOn())
                .deliveryAddress(deliveryPackageDTO.getDeliveryAddress())
                .payOnDelivery(deliveryPackageDTO.isPayOnDelivery())
                .deliveryPackageStatus(deliveryPackageDTO.getDeliveryPackageStatus())
                .clientEmail(deliveryPackageDTO.getClientEmail())
                .build();
    }
}