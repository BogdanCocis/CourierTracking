package com.utcn.Backend.controller;

import com.utcn.Backend.dto.DeliveryPackageDTO;
import com.utcn.Backend.entity.DeliveryPackageStatus;
import com.utcn.Backend.service.DeliveryPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin
public class DeliveryPackageController {

    @Autowired
    private DeliveryPackageService deliveryPackageService;

    @GetMapping
    public List<DeliveryPackageDTO> getAllPackages() {
        return deliveryPackageService.getAllPackage();
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
    public DeliveryPackageDTO updatePackageStatus(
            @PathVariable Integer packageId,
            @RequestBody DeliveryPackageDTO deliveryPackageDTO) {
        return deliveryPackageService.updatePackageStatus(packageId, deliveryPackageDTO.getDeliveryPackageStatus());
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