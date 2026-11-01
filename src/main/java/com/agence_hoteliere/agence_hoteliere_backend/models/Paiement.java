package com.agence_hoteliere.agence_hoteliere_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "paiements")
@Getter @Setter @NoArgsConstructor
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @DecimalMin("0.01") @Column(precision = 10, scale = 2)
    private BigDecimal montant;

    private LocalDateTime datePaiement = LocalDateTime.now();

    @Enumerated(EnumType.STRING) @NotNull
    private ModePaiement mode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}