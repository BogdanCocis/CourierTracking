package com.utcn.Backend.controller;

import com.utcn.Backend.dto.DeliveryPackageDTO;
import com.utcn.Backend.service.DeliveryPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public DeliveryPackageDTO createDeliveryPackage(DeliveryPackageDTO deliveryPackageDTO) {
        return deliveryPackageService.createNewPackage(deliveryPackageDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDeliveryPackage(@PathVariable Integer id) {
        DeliveryPackageDTO deliveryPackageDTO = new DeliveryPackageDTO();
        deliveryPackageDTO.setIdPackage(id);
        deliveryPackageService.deletePackage(deliveryPackageDTO);
    }

    @PutMapping("/{id}")
    public DeliveryPackageDTO updatePackageDetails(@PathVariable Integer id, @RequestBody DeliveryPackageDTO deliveryPackageDTO) {
        deliveryPackageDTO.setIdPackage(id);
        return deliveryPackageService.updatePackageDetails(deliveryPackageDTO);
    }
}
