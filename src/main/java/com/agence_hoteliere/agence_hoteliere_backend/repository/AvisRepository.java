package com.agence_hoteliere.agence_hoteliere_backend.repository;

import com.agence_hoteliere.agence_hoteliere_backend.models.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisRepository extends JpaRepository<Avis, Long> {
    List<Avis> findByHotelId(Long hotelId);
}
