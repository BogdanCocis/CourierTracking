package com.utcn.Backend.repository;

import com.utcn.Backend.entity.DeliveryPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackage, Integer> {
}
