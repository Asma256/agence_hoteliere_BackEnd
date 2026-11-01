package com.agence_hoteliere.agence_hoteliere_backend.services;

import com.agence_hoteliere.agence_hoteliere_backend.exception.ResourceNotFoundException;
import com.agence_hoteliere.agence_hoteliere_backend.models.Hotel;
import com.agence_hoteliere.agence_hoteliere_backend.repository.HotelRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {
    private final HotelRepository hotelRepo;

    public Hotel creer(Hotel h) { h.setId(null); return hotelRepo.save(h); }

    @Transactional(readOnly = true)
    public List<Hotel> lister() { return hotelRepo.findAll(); }

    @Transactional(readOnly = true)
    public Hotel trouver(Long id) {
        return hotelRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hôtel", id));
    }

    public Hotel modifier(Long id, Hotel data) {
        Hotel h = trouver(id);
        h.setNom(data.getNom()); h.setAdresse(data.getAdresse()); h.setVille(data.getVille());
        h.setEtoiles(data.getEtoiles()); h.setTelephone(data.getTelephone()); h.setEmail(data.getEmail());
        return hotelRepo.save(h);
    }

    public void supprimer(Long id) { hotelRepo.delete(trouver(id)); }

    @Transactional(readOnly = true)
    public List<Hotel> parVille(String ville) { return hotelRepo.findByVilleIgnoreCase(ville); }

    @Transactional(readOnly = true)
    public Double noteMoyenne(Long id) { trouver(id); return hotelRepo.noteMoyenne(id); }
}
