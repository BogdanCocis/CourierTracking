package com.utcn.Backend.repository;

import com.utcn.Backend.entity.DeliveryPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackage, Integer> {

    @Query("SELECT dp FROM DeliveryPackage dp WHERE dp.courier.idCourier = :courierId")
    List<DeliveryPackage> getPackagesForCourier(@Param("courierId") UUID courierId);

    @Query("SELECT c.idCourier, COUNT(dp) FROM DeliveryPackage dp RIGHT JOIN dp.courier c GROUP BY c.idCourier ORDER BY COUNT(dp) ASC")
    List<Object[]> findCourierWithFewestPackages();

    @Query("SELECT dp.idPackage, dp.deliveryAddress, dp.payOnDelivery, dp.deliveryPackageStatus, dp.clientEmail, " +
            "CASE WHEN dp.courier IS NOT NULL THEN dp.courier.name ELSE 'Not Assigned' END AS courierName " +
            "FROM DeliveryPackage dp")
    List<Object[]> getAllPackagesWithCourier();
}