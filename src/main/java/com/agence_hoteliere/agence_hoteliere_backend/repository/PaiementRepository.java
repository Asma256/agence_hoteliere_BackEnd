package com.agence_hoteliere.agence_hoteliere_backend.repository;

import com.agence_hoteliere.agence_hoteliere_backend.models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByReservationId(Long reservationId);

    // ➜ Requête personnalisée n°6 : total déjà payé
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE p.reservation.id = :id")
    BigDecimal totalPaye(@Param("id") Long reservationId);
}
