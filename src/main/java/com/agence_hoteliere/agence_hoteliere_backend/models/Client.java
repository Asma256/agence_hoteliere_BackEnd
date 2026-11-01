package com.agence_hoteliere.agence_hoteliere_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;
    @NotBlank private String prenom;

    @NotBlank @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[0-9+ ]{8,15}$", message = "Téléphone invalide")
    private String telephone;

    @Column(updatable = false)
    private LocalDate dateInscription = LocalDate.now();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();
}