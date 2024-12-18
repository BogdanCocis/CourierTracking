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

    public List<DeliveryPackageDTO> getAllPackagesWithCourier() {
        List<Object[]> results = deliveryPackageRepository.getAllPackagesWithCourier();
        return results.stream().map(row -> DeliveryPackageDTO.builder()
                .idPackage((Integer) row[0])
                .deliveryAddress((String) row[1])
                .payOnDelivery((Boolean) row[2])
                .deliveryPackageStatus((DeliveryPackageStatus) row[3])
                .clientEmail((String) row[4])
                .courierName((String) row[5])
                .build()
        ).collect(Collectors.toList());
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

        if (deliveryPackage.getDeliveryPackageStatus() == DeliveryPackageStatus.DELIVERED
                || deliveryPackage.getDeliveryPackageStatus() == DeliveryPackageStatus.DENIED) {
            throw new IllegalStateException("Cannot update a package that has already been delivered or denied.");
        }

        deliveryPackage.setDeliveryPackageStatus(status);
        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);

        String clientEmail = deliveryPackage.getClientEmail();
        switch (status) {
            case NEW:
            case PENDING:
                emailService.sendEmail(
                        clientEmail,
                        "Your package is on the way!",
                        "Hello,\n\nYour package is now in transit. Expect delivery soon.\n\nThank you!"
                );
                break;
            case DELIVERED:
                emailService.sendEmail(
                        clientEmail,
                        "Your package has been delivered!",
                        "Hello,\n\nYour package has been successfully delivered. Please rate our service!\n\nThank you!"
                );
                break;
            case NOT_HOME:
                emailService.sendEmail(
                        clientEmail,
                        "Attempted Delivery - We Missed You",
                        "Hello,\n\nWe attempted to deliver your package, but we couldn't find you at home. " +
                                "Please contact us or visit your nearest pick-up point to retrieve your package.\n\nThank you for choosing our services!"
                );
                break;
            case DENIED:
                emailService.sendEmail(
                        clientEmail,
                        "Your package delivery has been denied",
                        "Hello,\n\nUnfortunately, the delivery of your package has been denied. Please contact us for further assistance.\n\nThank you!"
                );
                break;
            default:
                throw new IllegalStateException("Unknown package status: " + status);
        }

        return deliveryPackageMapper.toPackageDTO(deliveryPackage);
    }


    @Transactional
    public void deletePackage(Integer packageId) {
        deliveryPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package with id " + packageId + " not found"));
        deliveryPackageRepository.deleteById(packageId);
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

    @Transactional
    public DeliveryPackageDTO updatePackageDetails(Integer packageId, DeliveryPackageDTO deliveryPackageDTO) {
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Package with id " + packageId + " not found"));

        if (deliveryPackageDTO.getDeliveryAddress() != null) {
            deliveryPackage.setDeliveryAddress(deliveryPackageDTO.getDeliveryAddress());
        }

        if (deliveryPackageDTO.getCourierId() != null) {
            Courier courier = courierRepository.findById(deliveryPackageDTO.getCourierId())
                    .orElseThrow(() -> new EntityNotFoundException("Courier not found"));
            deliveryPackage.setCourier(courier);
        }

        deliveryPackageRepository.save(deliveryPackage);
        return deliveryPackageMapper.toPackageDTO(deliveryPackage);
    }
}