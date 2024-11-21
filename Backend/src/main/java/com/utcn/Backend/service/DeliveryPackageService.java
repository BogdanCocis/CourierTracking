package com.utcn.Backend.service;

import com.utcn.Backend.dto.DeliveryPackageDTO;
import com.utcn.Backend.entity.Courier;
import com.utcn.Backend.entity.DeliveryPackage;
import com.utcn.Backend.entity.DeliveryPackageStatus;
import com.utcn.Backend.mapper.DeliveryPackageMapper;
import com.utcn.Backend.repository.CourierRepository;
import com.utcn.Backend.repository.DeliveryPackageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class DeliveryPackageService {
    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private DeliveryPackageMapper deviveryPackageMapper;

    @Autowired
    private CourierRepository courierRepository;

    public List<DeliveryPackageDTO> getAllPackage() {
        return deliveryPackageRepository.findAll()
                .stream()
                .map(deviveryPackageMapper::toPackageDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryPackageDTO createNewPackage(DeliveryPackageDTO deliveryPackageDTO) {
        List<Object[]> results = deliveryPackageRepository.findCourierWithFewestPackages();

        if (results.isEmpty()) {
            throw new IllegalStateException("No couriers available for assignment");
        }

        UUID courierId = (UUID) results.get(0)[0];
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new EntityNotFoundException("Courier with id " + courierId + " not found"));

        DeliveryPackage deliveryPackage = deviveryPackageMapper.toDeliveryPackage(deliveryPackageDTO);

        deliveryPackage.setDeliveryPackageStatus(DeliveryPackageStatus.NEW);

        deliveryPackage.setCourier(courier);

        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);

        return deviveryPackageMapper.toPackageDTO(deliveryPackage);
    }


    @Transactional
    public void deletePackageForCourier(Integer packageId, UUID courierId) {
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package with id " + packageId + " not found"));

        if (!deliveryPackage.getCourier().getIdCourier().equals(courierId)) {
            throw new IllegalStateException("Courier is not authorized to delete this package");
        }

        deliveryPackageRepository.deleteById(packageId);
    }

    @Transactional
    public DeliveryPackageDTO updatePackageDetails(DeliveryPackageDTO deliveryPackageDTO) {
        Integer packageId = deliveryPackageDTO.getIdPackage();
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package with id " + packageId + " not found"));
        if (deliveryPackage.getDeliveryPackageStatus() == DeliveryPackageStatus.DELIVERED) {
            throw new IllegalStateException("Cannot update a package that has been delivered");
        }
        deliveryPackage.setDeliveryAddress(deliveryPackageDTO.getDeliveryAddress());
        deliveryPackage.setDeliveryPackageStatus(deliveryPackageDTO.getDeliveryPackageStatus());
        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);
        return deviveryPackageMapper.toPackageDTO(deliveryPackage);
    }

    public List<DeliveryPackageDTO> getPackagesForCourier(UUID courierId) {
        return deliveryPackageRepository.getPackagesForCourier(courierId)
                .stream()
                .map(deviveryPackageMapper::toPackageDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryPackageDTO updatePackageStatus(Integer packageId, DeliveryPackageStatus status) {
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package with id " + packageId + " not found"));

        if (deliveryPackage.getDeliveryPackageStatus() == DeliveryPackageStatus.DELIVERED) {
            throw new IllegalStateException("Cannot update a package that has already been delivered");
        }

        deliveryPackage.setDeliveryPackageStatus(status);
        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);
        return deviveryPackageMapper.toPackageDTO(deliveryPackage);
    }


    @Transactional
    public DeliveryPackageDTO assignPackageToLeastBusyCourier(DeliveryPackageDTO deliveryPackageDTO) {
        List<Object[]> results = deliveryPackageRepository.findCourierWithFewestPackages();

        if (results.isEmpty()) {
            throw new IllegalStateException("No couriers available for assignment");
        }

        UUID courierId = (UUID) results.get(0)[0];
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new EntityNotFoundException("Courier with id " + courierId + " not found"));

        DeliveryPackage deliveryPackage = deviveryPackageMapper.toDeliveryPackage(deliveryPackageDTO);
        deliveryPackage.setCourier(courier);

        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);

        return deviveryPackageMapper.toPackageDTO(deliveryPackage);
    }
}
