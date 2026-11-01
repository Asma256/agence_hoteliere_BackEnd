package com.agence_hoteliere.agence_hoteliere_backend.controller;

import com.agence_hoteliere.agence_hoteliere_backend.dto.ReservationRequest;
import com.agence_hoteliere.agence_hoteliere_backend.dto.ReservationResponse;
import com.agence_hoteliere.agence_hoteliere_backend.models.StatutReservation;
import com.agence_hoteliere.agence_hoteliere_backend.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ---------- CREATE : 201 + en-tête Location ----------
    @PostMapping
    public ResponseEntity<ReservationResponse> creer(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse cree = reservationService.creer(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.id())
                .toUri();
        return ResponseEntity.created(location).body(cree);
    }

    // ---------- READ ----------
    @GetMapping
    public List<ReservationResponse> lister() {
        return reservationService.lister();
    }

    @GetMapping("/{id}")
    public ReservationResponse trouver(@PathVariable Long id) {
        return reservationService.trouver(id);
    }

    // GET /api/reservations/client/1
    @GetMapping("/client/{clientId}")
    public List<ReservationResponse> parClient(@PathVariable Long clientId) {
        return reservationService.parClient(clientId);
    }

    // GET /api/reservations/statut/CONFIRMEE
    @GetMapping("/statut/{statut}")
    public List<ReservationResponse> parStatut(@PathVariable StatutReservation statut) {
        return reservationService.parStatut(statut);
    }

    // ---------- CHANGEMENTS DE STATUT ----------

    // PATCH /api/reservations/1/annuler
    @PatchMapping("/{id}/annuler")
    public ReservationResponse annuler(@PathVariable Long id) {
        return reservationService.annuler(id);
    }

    // PATCH /api/reservations/1/terminer
    @PatchMapping("/{id}/terminer")
    public ReservationResponse terminer(@PathVariable Long id) {
        return reservationService.terminer(id);
    }

    // ---------- DELETE : 204 ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        reservationService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}