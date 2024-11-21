package com.utcn.Backend.repository;

import com.utcn.Backend.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {
    Optional<Courier> findByEmail(String email);
        boolean existsByEmail(String email);

    @Query("SELECT c FROM Courier c WHERE c.idCourier NOT IN (SELECT dp.courier.idCourier FROM DeliveryPackage dp WHERE dp.deliveryPackageStatus = 'PENDING')")
    List<Courier> getAllCouriersWithoutPendingPackages();

    @Query("SELECT c.manager.name AS managerName, c.manager.email AS managerEmail, COUNT(dp) AS deliveredCount " +
            "FROM Courier c " +
            "JOIN DeliveryPackage dp ON dp.courier.idCourier = c.idCourier " +
            "WHERE dp.deliveryPackageStatus = 'DELIVERED' " +
            "AND c.manager IS NOT NULL " +
            "GROUP BY c.manager.idCourier, c.manager.name, c.manager.email")
    List<Object[]> getAllManagersAndDeliveredNumber();
}
