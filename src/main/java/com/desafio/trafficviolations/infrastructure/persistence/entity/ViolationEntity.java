package com.desafio.trafficviolations.infrastructure.persistence.entity;

import com.desafio.trafficviolations.domain.enums.ViolationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "violations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViolationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occurrence_date_utc", nullable = false)
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ViolationType type;

    private BigDecimal measuredSpeed;
    private BigDecimal consideredSpeed;
    private BigDecimal regulatedSpeed;

    @Lob
    @Column(name = "picture", columnDefinition = "bytea")
    private byte[] picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;
}
