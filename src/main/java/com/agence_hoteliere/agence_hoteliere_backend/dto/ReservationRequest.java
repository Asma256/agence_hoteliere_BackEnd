package com.agence_hoteliere.agence_hoteliere_backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record ReservationRequest(
        @NotNull Long clientId,
        @NotNull Long chambreId,
        @NotNull @FutureOrPresent LocalDate dateArrivee,
        @NotNull @Future LocalDate dateDepart,
        @Min(1) int nombrePersonnes,
        Set<Long> serviceIds) { }
