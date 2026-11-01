package com.agence_hoteliere.agence_hoteliere_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chambres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"numero", "hotel_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String numero;

    @Enumerated(EnumType.STRING) @NotNull
    private TypeChambre type;

    @NotNull @DecimalMin(value = "1.0", message = "Prix invalide")
    @Column(precision = 10, scale = 2)
    private BigDecimal prixParNuit;

    @Min(1) private int capacite;

    private boolean disponible = true;   // false = hors service / maintenance

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "chambre")
    private List<Reservation> reservations = new ArrayList<>();
}
