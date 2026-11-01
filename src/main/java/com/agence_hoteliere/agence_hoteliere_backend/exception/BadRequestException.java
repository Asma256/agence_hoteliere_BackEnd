package com.agence_hoteliere.agence_hoteliere_backend.exception;

public class BadRequestException extends RuntimeException {      // règle métier violée → 400
    public BadRequestException(String message) { super(message); }
}
