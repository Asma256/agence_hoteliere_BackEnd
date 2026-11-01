package com.agence_hoteliere.agence_hoteliere_backend.repository;

import com.agence_hoteliere.agence_hoteliere_backend.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface
HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByVilleIgnoreCase(String ville);                    // requête dérivée
    List<Hotel> findByEtoilesGreaterThanEqual(int etoiles);

    // ➜ Requête personnalisée n°1 : note moyenne d'un hôtel
    @Query("SELECT AVG(a.note) FROM Avis a WHERE a.hotel.id = :hotelId")
    Double noteMoyenne(@Param("hotelId") Long hotelId);
}

