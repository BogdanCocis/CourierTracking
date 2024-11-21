package com.utcn.Backend.controller;

import com.utcn.Backend.dto.CourierDTO;
import com.utcn.Backend.dto.ManagerDeliveredDTO;
import com.utcn.Backend.mapper.CourierMapper;
import com.utcn.Backend.service.CourierService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/couriers")
@CrossOrigin
public class CourierController {

    private final CourierService courierService;
    private final CourierMapper courierMapper;

    public CourierController(CourierService courierService, CourierMapper courierMapper) {
        this.courierService = courierService;
        this.courierMapper = courierMapper;
    }

    @GetMapping("/without-pending-packages")
    public List<CourierDTO> getAllCouriersWithoutPendingPackages() {
        return courierService.getCouriersWithoutPendingPackages();
    }

    @GetMapping("/managers-delivered-count")
    public List<ManagerDeliveredDTO> getAllManagersAndDeliveredNumber() {
        return courierService.getAllManagersAndDeliveredNumber();
    }
}
