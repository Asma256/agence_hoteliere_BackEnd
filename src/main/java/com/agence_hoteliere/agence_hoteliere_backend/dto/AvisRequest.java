package com.agence_hoteliere.agence_hoteliere_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AvisRequest(@NotNull Long clientId, @NotNull Long hotelId,
                          @Min(1) @Max(5) int note, @Size(max = 500) String commentaire) { }
