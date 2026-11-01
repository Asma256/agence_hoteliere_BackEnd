package com.agence_hoteliere.agence_hoteliere_backend.services;

import com.agence_hoteliere.agence_hoteliere_backend.exception.ConflictException;
import com.agence_hoteliere.agence_hoteliere_backend.exception.ResourceNotFoundException;
import com.agence_hoteliere.agence_hoteliere_backend.models.Client;
import com.agence_hoteliere.agence_hoteliere_backend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepo;

    // ---------- CREATE ----------
    public Client creer(Client client) {
        String email = normaliser(client.getEmail());
        if (clientRepo.existsByEmail(email)) {
            throw new ConflictException("Un client avec l'email '" + email + "' existe déjà");
        }
        client.setId(null);              // empêche d'écraser un client existant via l'id du JSON
        client.setEmail(email);
        Client saved = clientRepo.save(client);
        log.info("Client créé : id={}, email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    // ---------- READ ----------
    @Transactional(readOnly = true)
    public List<Client> lister() {
        return clientRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Client trouver(Long id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Transactional(readOnly = true)
    public Client trouverParEmail(String email) {
        return clientRepo.findByEmail(normaliser(email))
                .orElseThrow(() -> new ResourceNotFoundException("Client (email " + email + ")", 0L));
    }

    @Transactional(readOnly = true)
    public List<Client> rechercher(String motCle) {
        return clientRepo
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(motCle, motCle);
    }

    // ---------- UPDATE ----------
    public Client modifier(Long id, Client data) {
        Client existant = trouver(id);

        String nouvelEmail = normaliser(data.getEmail());
        if (clientRepo.existsByEmailAndIdNot(nouvelEmail, id)) {
            throw new ConflictException("L'email '" + nouvelEmail + "' est déjà utilisé par un autre client");
        }

        existant.setNom(data.getNom());
        existant.setPrenom(data.getPrenom());
        existant.setEmail(nouvelEmail);
        existant.setTelephone(data.getTelephone());
        // dateInscription n'est jamais modifiée

        log.info("Client modifié : id={}", id);
        return clientRepo.save(existant);
    }

    // ---------- DELETE ----------
    public void supprimer(Long id) {
        Client client = trouver(id);
        if (!client.getReservations().isEmpty()) {
            throw new ConflictException(
                    "Suppression impossible : ce client possède " + client.getReservations().size()
                            + " réservation(s)");
        }
        clientRepo.delete(client);
        log.info("Client supprimé : id={}", id);
    }

    // ---------- Utilitaire ----------
    private String normaliser(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }
}
