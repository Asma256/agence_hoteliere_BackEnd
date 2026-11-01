package com.agence_hoteliere.agence_hoteliere_backend.controller;

import com.agence_hoteliere.agence_hoteliere_backend.dto.ChambreResponse;
import com.agence_hoteliere.agence_hoteliere_backend.models.Hotel;
import com.agence_hoteliere.agence_hoteliere_backend.services.ChambreService;
import com.agence_hoteliere.agence_hoteliere_backend.services.HotelService;
import com.agence_hoteliere.agence_hoteliere_backend.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final ChambreService chambreService;
    private final ReservationService reservationService;

    // ---------- CREATE : 201 + en-tête Location ----------
    @PostMapping
    public ResponseEntity<Hotel> creer(@Valid @RequestBody Hotel hotel) {
        Hotel cree = hotelService.creer(hotel);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.getId())
                .toUri();
        return ResponseEntity.created(location).body(cree);
    }

    // ---------- READ ----------
    @GetMapping
    public List<Hotel> lister() {
        return hotelService.lister();
    }

    @GetMapping("/{id}")
    public Hotel trouver(@PathVariable Long id) {
        return hotelService.trouver(id);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public Hotel modifier(@PathVariable Long id, @Valid @RequestBody Hotel hotel) {
        return hotelService.modifier(id, hotel);
    }

    // ---------- DELETE : 204 ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        hotelService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- REQUÊTES MÉTIER ----------

    // GET /api/hotels/ville/Tunis
    @GetMapping("/ville/{ville}")
    public List<Hotel> parVille(@PathVariable String ville) {
        return hotelService.parVille(ville);
    }

    // GET /api/hotels/1/note-moyenne
    @GetMapping("/{id}/note-moyenne")
    public Map<String, Object> noteMoyenne(@PathVariable Long id) {
        Double moyenne = hotelService.noteMoyenne(id);   // 404 si l'hôtel n'existe pas
        return Map.of(
                "hotelId", id,
                "noteMoyenne", moyenne == null ? 0.0 : Math.round(moyenne * 10) / 10.0);
    }

    // GET /api/hotels/1/chiffre-affaires
    @GetMapping("/{id}/chiffre-affaires")
    public Map<String, Object> chiffreAffaires(@PathVariable Long id) {
        hotelService.trouver(id);                        // 404 si l'hôtel n'existe pas
        BigDecimal ca = reservationService.chiffreAffaires(id);
        return Map.of("hotelId", id, "chiffreAffaires", ca);
    }

    // GET /api/hotels/1/chambres-disponibles?arrivee=2026-12-10&depart=2026-12-13
    @GetMapping("/{id}/chambres-disponibles")
    public List<ChambreResponse> chambresDisponibles(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate depart) {
        hotelService.trouver(id);                        // 404 si l'hôtel n'existe pas
        return chambreService.disponibles(id, arrivee, depart);
    }
}