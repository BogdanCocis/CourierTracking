package com.utcn.Backend.controller;

import com.utcn.Backend.dto.DeliveryPackageDTO;
import com.utcn.Backend.service.DeliveryPackageService;
import com.utcn.Backend.service.SessionIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class DeliveryPackageController {

    @Autowired
    private DeliveryPackageService deliveryPackageService;

    @Autowired
    private SessionIdService sessionIdService;


    @GetMapping
    public List<DeliveryPackageDTO> getAllPackages() {
        return deliveryPackageService.getAllPackage();
    }

    @GetMapping("/with-courier")
    public List<DeliveryPackageDTO> getAllPackagesWithCourier() {
        return deliveryPackageService.getAllPackagesWithCourier();
    }

    @PostMapping
    public DeliveryPackageDTO createDeliveryPackage(@RequestBody DeliveryPackageDTO deliveryPackageDTO) {
        return deliveryPackageService.createNewPackage(deliveryPackageDTO);
    }

    @DeleteMapping("/{packageId}/courier/{courierId}")
    public void deletePackageForCourier(
            @PathVariable Integer packageId,
            @PathVariable String courierId) {
        String formattedCourierId = courierId.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"
        );

        UUID uuid = UUID.fromString(formattedCourierId);
        deliveryPackageService.deletePackageForCourier(packageId, uuid);
    }

    @PutMapping("/{packageId}/status")
    public ResponseEntity<DeliveryPackageDTO> updatePackageStatus(
            @PathVariable Integer packageId,
            @RequestBody DeliveryPackageDTO deliveryPackageDTO) {
        if (deliveryPackageDTO.getDeliveryPackageStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        DeliveryPackageDTO updatedPackage = deliveryPackageService.updatePackageStatus(
                packageId, deliveryPackageDTO.getDeliveryPackageStatus());

        return ResponseEntity.ok(updatedPackage);
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<DeliveryPackageDTO> updatePackageDetails(
            @PathVariable Integer packageId, @RequestBody DeliveryPackageDTO deliveryPackageDTO) {
        DeliveryPackageDTO updatedPackage = deliveryPackageService.updatePackageDetails(packageId, deliveryPackageDTO);
        return ResponseEntity.ok(updatedPackage);
    }

    @DeleteMapping("/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable Integer packageId) {
        deliveryPackageService.deletePackage(packageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courier/{courierId}")
    public List<DeliveryPackageDTO> getPackagesForCourier(@PathVariable String courierId) {
        String formattedCourierId = courierId.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"
        );
        UUID uuid = UUID.fromString(formattedCourierId);
        return deliveryPackageService.getPackagesForCourier(uuid);
    }

    @PostMapping("/assign")
    public DeliveryPackageDTO assignPackage(@RequestBody DeliveryPackageDTO deliveryPackageDTO) {
        return deliveryPackageService.assignPackageToLeastBusyCourier(deliveryPackageDTO);
    }
}