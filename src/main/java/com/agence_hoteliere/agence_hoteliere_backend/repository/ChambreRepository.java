package com.agence_hoteliere.agence_hoteliere_backend.repository;

import com.agence_hoteliere.agence_hoteliere_backend.models.Chambre;
import com.agence_hoteliere.agence_hoteliere_backend.models.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    List<Chambre> findByHotelId(Long hotelId);
    List<Chambre> findByTypeAndDisponibleTrue(TypeChambre type);

    @Query("""
    SELECT c FROM Chambre c
    WHERE c.hotel.id = :hotelId AND c.disponible = true
      AND c.id NOT IN (
          SELECT r.chambre.id FROM Reservation r
          WHERE r.statut <> com.agence_hoteliere.agence_hoteliere_backend.models.StatutReservation.ANNULEE
            AND r.dateArrivee < :depart AND r.dateDepart > :arrivee)
    """)
    List<Chambre> findDisponibles(@Param("hotelId") Long hotelId,
                                  @Param("arrivee") LocalDate arrivee,
                                  @Param("depart") LocalDate depart);
    List<Chambre> findByPrixParNuitBetweenAndDisponibleTrue(BigDecimal min, BigDecimal max);
}
