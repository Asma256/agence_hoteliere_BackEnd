package com.agence_hoteliere.agence_hoteliere_backend.controller;

import com.agence_hoteliere.agence_hoteliere_backend.dto.ChambreRequest;
import com.agence_hoteliere.agence_hoteliere_backend.dto.ChambreResponse;
import com.agence_hoteliere.agence_hoteliere_backend.services.ChambreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/chambres")
@RequiredArgsConstructor
public class ChambreController {

    private final ChambreService chambreService;

    // ---------- CREATE : 201 + en-tête Location ----------
    @PostMapping
    public ResponseEntity<ChambreResponse> creer(@Valid @RequestBody ChambreRequest request) {
        ChambreResponse cree = chambreService.creer(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cree.id())
                .toUri();
        return ResponseEntity.created(location).body(cree);
    }

    // ---------- READ ----------
    @GetMapping
    public List<ChambreResponse> lister() {
        return chambreService.lister();
    }

    @GetMapping("/{id}")
    public ChambreResponse trouver(@PathVariable Long id) {
        return chambreService.trouver(id);
    }


    // GET /api/chambres/budget?min=50&max=150
    @GetMapping("/budget")
    public List<ChambreResponse> parBudget(@RequestParam BigDecimal min,
                                           @RequestParam BigDecimal max) {
        return chambreService.parBudget(min, max);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ChambreResponse modifier(@PathVariable Long id,
                                    @Valid @RequestBody ChambreRequest request) {
        return chambreService.modifier(id, request);
    }

    // ---------- DELETE : 204 ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        chambreService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}