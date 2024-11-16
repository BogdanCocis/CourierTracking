package com.utcn.Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Session {
    @Id
    @Column(name = "id_session", nullable = false)
    private UUID sessionId;

    @Column(name = "expitation_time")
    private LocalDateTime expirationTime;

    @OneToOne
    @JoinColumn(name = "id_courier")
    @ToString.Exclude
    private Courier courier;
}
