package com.agence_hoteliere.agence_hoteliere_backend.dto;

import com.agence_hoteliere.agence_hoteliere_backend.models.Reservation;
import com.agence_hoteliere.agence_hoteliere_backend.models.ServiceHotel;
import com.agence_hoteliere.agence_hoteliere_backend.models.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public record ReservationResponse(
        Long id,
        Long clientId,
        String clientNom,
        Long chambreId,
        String numeroChambre,
        String hotelNom,
        LocalDate dateArrivee,
        LocalDate dateDepart,
        int nombrePersonnes,
        BigDecimal montantTotal,
        StatutReservation statut,
        List<String> services) {

    public static ReservationResponse from(Reservation r) {
        List<String> noms = r.getServices() == null
                ? List.of()
                : r.getServices().stream()
                .map(ServiceHotel::getNom)
                .sorted(Comparator.naturalOrder())
                .toList();

        return new ReservationResponse(
                r.getId(),
                r.getClient().getId(),
                r.getClient().getPrenom() + " " + r.getClient().getNom(),
                r.getChambre().getId(),
                r.getChambre().getNumero(),
                r.getChambre().getHotel().getNom(),
                r.getDateArrivee(),
                r.getDateDepart(),
                r.getNombrePersonnes(),
                r.getMontantTotal(),
                r.getStatut(),
                noms);
    }
}