package com.rocketseat.planner.exception;

import java.util.UUID;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(UUID id) {
        super("Trip not found with ID: " + id);
    }
    
    public TripNotFoundException(String message) {
        super(message);
    }
}