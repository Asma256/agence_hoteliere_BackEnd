package com.agence_hoteliere.agence_hoteliere_backend.dto;

import com.agence_hoteliere.agence_hoteliere_backend.models.ModePaiement;
import com.agence_hoteliere.agence_hoteliere_backend.models.Paiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaiementResponse(Long id, Long reservationId, BigDecimal montant,
                               ModePaiement mode, LocalDateTime datePaiement) {
    public static PaiementResponse from(Paiement p) {
        return new PaiementResponse(p.getId(), p.getReservation().getId(), p.getMontant(),
                p.getMode(), p.getDatePaiement());
    }
}
