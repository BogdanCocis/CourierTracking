package com.utcn.proiectScd.service;

import com.utcn.proiectScd.dto.DeliveryPackageDTO;
import com.utcn.proiectScd.entity.DeliveryPackage;
import com.utcn.proiectScd.mapper.DeviveryPackageMapper;
import com.utcn.proiectScd.repository.DeliveryPackageRepository;
import com.utcn.proiectScd.util.DeliveryPackageStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class DeviveryPackageService {
    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private DeviveryPackageMapper deviveryPackageMapper;

    public List<DeliveryPackageDTO> getAllPackage() {
        return deliveryPackageRepository.findAll()
                .stream()
                .map(deviveryPackageMapper::toPackageDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryPackageDTO createNewPackage(DeliveryPackageDTO deliveryPackageDTO) {
        DeliveryPackage deliveryPackage = deviveryPackageMapper.toDeliveryPackage(deliveryPackageDTO);
        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);
        return deviveryPackageMapper.toPackageDTO(deliveryPackage);
    }

    @Transactional
    public void deletePackage(DeliveryPackageDTO deliveryPackageDTO) {
        Integer id = deliveryPackageDTO.getIdPackage();
        if (deliveryPackageDTO.getIdPackage() == null) {
            deliveryPackageRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Package with id " + id + " not found");
        }
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
}
