package com.utcn.proiectScd.entity;

import com.utcn.proiectScd.util.CourierRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idCourier", unique = true, nullable = false)
    private UUID idCourier;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourierRole courierRole;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Courier manager;
}
