package com.utcn.proiectScd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idCourier", unique = true, nullable = false)
    private UUID idCourier;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Courier manager;
}
