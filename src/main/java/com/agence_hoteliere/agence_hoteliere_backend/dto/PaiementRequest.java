package com.agence_hoteliere.agence_hoteliere_backend.dto;

import com.agence_hoteliere.agence_hoteliere_backend.models.ModePaiement;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaiementRequest(@NotNull Long reservationId,
                              @NotNull @DecimalMin("0.01") BigDecimal montant,
                              @NotNull ModePaiement mode) { }
