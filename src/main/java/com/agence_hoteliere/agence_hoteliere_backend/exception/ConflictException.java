package com.agence_hoteliere.agence_hoteliere_backend.exception;

public class ConflictException extends RuntimeException {        // conflit (doublon, chambre prise) → 409
    public ConflictException(String message) { super(message); }
}
