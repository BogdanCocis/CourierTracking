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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryPackageService {

    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private DeliveryPackageMapper deliveryPackageMapper;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.mail.sender}")
    private String senderEmail;

    public List<DeliveryPackageDTO> getAllPackage() {
        return deliveryPackageRepository.findAll()
                .stream()
                .map(deliveryPackageMapper::toPackageDTO)
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

        DeliveryPackage deliveryPackage = deliveryPackageMapper.toDeliveryPackage(deliveryPackageDTO);

        deliveryPackage.setDeliveryPackageStatus(DeliveryPackageStatus.NEW);

        deliveryPackage.setCourier(courier);

        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);

        return deliveryPackageMapper.toPackageDTO(deliveryPackage);
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
    public DeliveryPackageDTO updatePackageStatus(Integer packageId, DeliveryPackageStatus status) {
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package with id " + packageId + " not found"));

        if (deliveryPackage.getDeliveryPackageStatus() == DeliveryPackageStatus.DELIVERED) {
            throw new IllegalStateException("Cannot update a package that has already been delivered");
        }

        deliveryPackage.setDeliveryPackageStatus(status);
        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);

        String clientEmail = deliveryPackage.getClientEmail();
        if (status == DeliveryPackageStatus.PENDING) {
            emailService.sendEmail(
                    clientEmail,
                    "Your package is on the way!",
                    "Hello, your package is now in transit. Expect delivery soon. Thank you!"
            );
        } else if (status == DeliveryPackageStatus.DELIVERED) {
            emailService.sendEmail(
                    clientEmail,
                    "Your package has been delivered!",
                    "Hello, your package has been successfully delivered. Please rate our service! Thank you!"
            );
        }

        return deliveryPackageMapper.toPackageDTO(deliveryPackage);
    }

    public List<DeliveryPackageDTO> getPackagesForCourier(UUID courierId) {
        return deliveryPackageRepository.getPackagesForCourier(courierId)
                .stream()
                .map(deliveryPackageMapper::toPackageDTO)
                .collect(Collectors.toList());
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

        DeliveryPackage deliveryPackage = deliveryPackageMapper.toDeliveryPackage(deliveryPackageDTO);
        deliveryPackage.setCourier(courier);

        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);

        return deliveryPackageMapper.toPackageDTO(deliveryPackage);
    }
}