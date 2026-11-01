package com.agence_hoteliere.agence_hoteliere_backend.controller;

import com.agence_hoteliere.agence_hoteliere_backend.dto.ReservationResponse;
import com.agence_hoteliere.agence_hoteliere_backend.models.Client;
import com.agence_hoteliere.agence_hoteliere_backend.services.ClientService;
import com.agence_hoteliere.agence_hoteliere_backend.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Client> creer(@Valid @RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.creer(client));
    }

    @GetMapping
    public List<Client> lister() { return clientService.lister(); }

    @GetMapping("/{id}")
    public Client trouver(@PathVariable Long id) { return clientService.trouver(id); }

    @GetMapping("/recherche")
    public List<Client> rechercher(@RequestParam String motCle) { return clientService.rechercher(motCle); }

    @GetMapping("/{id}/reservations")
    public List<ReservationResponse> reservations(@PathVariable Long id) {
        clientService.trouver(id);                       // 404 si le client n'existe pas
        return reservationService.parClient(id);
    }

    @PutMapping("/{id}")
    public Client modifier(@PathVariable Long id, @Valid @RequestBody Client client) {
        return clientService.modifier(id, client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        clientService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
