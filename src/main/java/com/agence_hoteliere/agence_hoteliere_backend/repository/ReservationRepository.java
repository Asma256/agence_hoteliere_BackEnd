package com.agence_hoteliere.agence_hoteliere_backend.repository;

import com.agence_hoteliere.agence_hoteliere_backend.models.Reservation;
import com.agence_hoteliere.agence_hoteliere_backend.models.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientId(Long clientId);

    List<Reservation> findByStatut(StatutReservation statut);

    // Détection de chevauchement (anti double-réservation)
    @Query("""
        SELECT COUNT(r) > 0 FROM Reservation r
        WHERE r.chambre.id = :chambreId
          AND r.statut <> com.agence_hoteliere.agence_hoteliere_backend.models.StatutReservation.ANNULEE
          AND r.dateArrivee < :depart
          AND r.dateDepart > :arrivee
        """)
    boolean existeChevauchement(@Param("chambreId") Long chambreId,
                                @Param("arrivee") LocalDate arrivee,
                                @Param("depart") LocalDate depart);

    // Chiffre d'affaires d'un hôtel
    @Query("""
        SELECT COALESCE(SUM(r.montantTotal), 0) FROM Reservation r
        WHERE r.chambre.hotel.id = :hotelId
          AND r.statut = com.agence_hoteliere.agence_hoteliere_backend.models.StatutReservation.CONFIRMEE
        """)
    BigDecimal chiffreAffaires(@Param("hotelId") Long hotelId);

    boolean existsByClientIdAndChambreHotelIdAndStatut(Long clientId, Long hotelId, StatutReservation statut);
}