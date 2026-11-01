package com.agence_hoteliere.agence_hoteliere_backend.services;

import com.agence_hoteliere.agence_hoteliere_backend.dto.ReservationRequest;
import com.agence_hoteliere.agence_hoteliere_backend.dto.ReservationResponse;
import com.agence_hoteliere.agence_hoteliere_backend.exception.BadRequestException;
import com.agence_hoteliere.agence_hoteliere_backend.exception.ConflictException;
import com.agence_hoteliere.agence_hoteliere_backend.exception.ResourceNotFoundException;
import com.agence_hoteliere.agence_hoteliere_backend.models.*;
import com.agence_hoteliere.agence_hoteliere_backend.repository.ChambreRepository;
import com.agence_hoteliere.agence_hoteliere_backend.repository.ClientRepository;
import com.agence_hoteliere.agence_hoteliere_backend.repository.ReservationRepository;
import com.agence_hoteliere.agence_hoteliere_backend.repository.ServiceHotelRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final ClientRepository clientRepo;
    private final ChambreRepository chambreRepo;
    private final ServiceHotelRepository serviceRepo;

    public ReservationResponse creer(ReservationRequest req) {
        if (!req.dateDepart().isAfter(req.dateArrivee()))
            throw new BadRequestException("La date de départ doit être postérieure à la date d'arrivée");

        Client client = clientRepo.findById(req.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", req.clientId()));
        Chambre chambre = chambreRepo.findById(req.chambreId())
                .orElseThrow(() -> new ResourceNotFoundException("Chambre", req.chambreId()));

        if (!chambre.isDisponible())
            throw new ConflictException("Cette chambre est hors service");
        if (req.nombrePersonnes() > chambre.getCapacite())
            throw new BadRequestException("Capacité dépassée (max " + chambre.getCapacite() + " personnes)");
        if (reservationRepo.existeChevauchement(chambre.getId(), req.dateArrivee(), req.dateDepart()))
            throw new ConflictException("Chambre déjà réservée sur cette période");

        Set<ServiceHotel> services = new HashSet<>();
        if (req.serviceIds() != null && !req.serviceIds().isEmpty()) {
            services.addAll(serviceRepo.findAllById(req.serviceIds()));
            if (services.size() != req.serviceIds().size())
                throw new BadRequestException("Un ou plusieurs services sont introuvables");
        }

        long nuits = ChronoUnit.DAYS.between(req.dateArrivee(), req.dateDepart());
        BigDecimal total = chambre.getPrixParNuit().multiply(BigDecimal.valueOf(nuits));
        for (ServiceHotel s : services) total = total.add(s.getPrix());

        Reservation r = new Reservation();
        r.setClient(client); r.setChambre(chambre);
        r.setDateArrivee(req.dateArrivee()); r.setDateDepart(req.dateDepart());
        r.setNombrePersonnes(req.nombrePersonnes());
        r.setServices(services); r.setMontantTotal(total);
        r.setStatut(StatutReservation.EN_ATTENTE);
        return ReservationResponse.from(reservationRepo.save(r));
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> lister() {
        return reservationRepo.findAll().stream().map(ReservationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ReservationResponse trouver(Long id) { return ReservationResponse.from(charger(id)); }

    @Transactional(readOnly = true)
    public List<ReservationResponse> parClient(Long clientId) {
        return reservationRepo.findByClientId(clientId).stream().map(ReservationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> parStatut(StatutReservation s) {
        return reservationRepo.findByStatut(s).stream().map(ReservationResponse::from).toList();
    }

    public ReservationResponse annuler(Long id) {
        Reservation r = charger(id);
        if (r.getStatut() == StatutReservation.TERMINEE)
            throw new BadRequestException("Impossible d'annuler une réservation terminée");
        r.setStatut(StatutReservation.ANNULEE);
        return ReservationResponse.from(r);
    }

    public ReservationResponse terminer(Long id) {
        Reservation r = charger(id);
        if (r.getStatut() != StatutReservation.CONFIRMEE)
            throw new BadRequestException("Seule une réservation confirmée peut être terminée");
        r.setStatut(StatutReservation.TERMINEE);
        return ReservationResponse.from(r);
    }

    public void supprimer(Long id) { reservationRepo.delete(charger(id)); }

    @Transactional(readOnly = true)
    public BigDecimal chiffreAffaires(Long hotelId) { return reservationRepo.chiffreAffaires(hotelId); }

    private Reservation charger(Long id) {
        return reservationRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Réservation", id));
    }
}