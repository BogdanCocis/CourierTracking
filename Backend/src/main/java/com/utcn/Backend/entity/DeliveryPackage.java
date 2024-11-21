package com.utcn.Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPackage", unique = true, nullable = false)
    private Integer idPackage;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
   private Courier courier;

    @Column(name = "createdOn", nullable = false)
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "deliveryAddress", nullable = false)
    private String deliveryAddress;

    @Column(name = "payOnDelivery", nullable = false)
    private boolean payOnDelivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "packageStatus", nullable = false)
    private DeliveryPackageStatus deliveryPackageStatus;

    @Column(name = "clientEmail", nullable = false)
    private String clientEmail;
}
