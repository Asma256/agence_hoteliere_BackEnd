package com.agence_hoteliere.agence_hoteliere_backend.dto;

import com.agence_hoteliere.agence_hoteliere_backend.models.TypeChambre;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ChambreRequest(
        @NotBlank String numero,
        @NotNull TypeChambre type,
        @NotNull @DecimalMin("1.0") BigDecimal prixParNuit,
        @Min(1) int capacite,
        boolean disponible,
        @NotNull Long hotelId) { }
