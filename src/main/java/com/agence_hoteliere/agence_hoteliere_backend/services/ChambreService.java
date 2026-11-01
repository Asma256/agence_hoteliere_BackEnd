package com.agence_hoteliere.agence_hoteliere_backend.services;

import com.agence_hoteliere.agence_hoteliere_backend.dto.ChambreRequest;
import com.agence_hoteliere.agence_hoteliere_backend.dto.ChambreResponse;
import com.agence_hoteliere.agence_hoteliere_backend.exception.BadRequestException;
import com.agence_hoteliere.agence_hoteliere_backend.exception.ResourceNotFoundException;
import com.agence_hoteliere.agence_hoteliere_backend.models.Chambre;
import com.agence_hoteliere.agence_hoteliere_backend.models.Hotel;
import com.agence_hoteliere.agence_hoteliere_backend.repository.ChambreRepository;
import com.agence_hoteliere.agence_hoteliere_backend.repository.HotelRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChambreService {
    private final ChambreRepository chambreRepo;
    private final HotelRepository hotelRepo;

    public ChambreResponse creer(ChambreRequest req) {
        Hotel hotel = hotelRepo.findById(req.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hôtel", req.hotelId()));
        Chambre c = new Chambre();
        appliquer(c, req, hotel);
        return ChambreResponse.from(chambreRepo.save(c));
    }

    public ChambreResponse modifier(Long id, ChambreRequest req) {
        Chambre c = chambreRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chambre", id));
        Hotel hotel = hotelRepo.findById(req.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hôtel", req.hotelId()));
        appliquer(c, req, hotel);
        return ChambreResponse.from(chambreRepo.save(c));
    }

    @Transactional(readOnly = true)
    public List<ChambreResponse> lister() {
        return chambreRepo.findAll().stream().map(ChambreResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ChambreResponse trouver(Long id) {
        return chambreRepo.findById(id).map(ChambreResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Chambre", id));
    }

    public void supprimer(Long id) {
        Chambre c = chambreRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chambre", id));
        chambreRepo.delete(c);   // échoue en 409 si des réservations existent
    }

    @Transactional(readOnly = true)
    public List<ChambreResponse> disponibles(Long hotelId, LocalDate arrivee, LocalDate depart) {
        if (!depart.isAfter(arrivee)) throw new BadRequestException("Période invalide");
        return chambreRepo.findDisponibles(hotelId, arrivee, depart).stream().map(ChambreResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ChambreResponse> parBudget(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) > 0) throw new BadRequestException("min doit être ≤ max");
        return chambreRepo.findByPrixParNuitBetweenAndDisponibleTrue(min, max)
                .stream().map(ChambreResponse::from).toList();
    }

    private void appliquer(Chambre c, ChambreRequest r, Hotel h) {
        c.setNumero(r.numero()); c.setType(r.type()); c.setPrixParNuit(r.prixParNuit());
        c.setCapacite(r.capacite()); c.setDisponible(r.disponible()); c.setHotel(h);
    }
}
