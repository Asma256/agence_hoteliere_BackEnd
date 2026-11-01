package com.agence_hoteliere.agence_hoteliere_backend.dto;

import com.agence_hoteliere.agence_hoteliere_backend.models.Chambre;
import com.agence_hoteliere.agence_hoteliere_backend.models.TypeChambre;

import java.math.BigDecimal;

public record ChambreResponse(Long id, String numero, TypeChambre type, BigDecimal prixParNuit,
                              int capacite, boolean disponible, Long hotelId, String hotelNom) {
    public static ChambreResponse from(Chambre c) {
        return new ChambreResponse(c.getId(), c.getNumero(), c.getType(), c.getPrixParNuit(),
                c.getCapacite(), c.isDisponible(), c.getHotel().getId(), c.getHotel().getNom());
    }
}
