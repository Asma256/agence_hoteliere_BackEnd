package com.agence_hoteliere.agence_hoteliere_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "avis")
@Getter
@Setter
@NoArgsConstructor
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1) @Max(5) private int note;

    @Size(max = 500) private String commentaire;

    private LocalDate dateAvis = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
