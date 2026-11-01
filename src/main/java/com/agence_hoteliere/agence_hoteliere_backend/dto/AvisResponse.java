package com.agence_hoteliere.agence_hoteliere_backend.dto;

import com.agence_hoteliere.agence_hoteliere_backend.models.Avis;

import java.time.LocalDate;

public record AvisResponse(Long id, String client, Long hotelId, int note,
                           String commentaire, LocalDate dateAvis) {
    public static AvisResponse from(Avis a) {
        return new AvisResponse(a.getId(), a.getClient().getPrenom() + " " + a.getClient().getNom(),
                a.getHotel().getId(), a.getNote(), a.getCommentaire(), a.getDateAvis());
    }
}
