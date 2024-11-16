package com.utcn.Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "manager_id")
    private Courier manager;
}
