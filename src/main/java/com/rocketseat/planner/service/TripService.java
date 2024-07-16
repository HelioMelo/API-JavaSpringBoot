package com.rocketseat.planner.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.domain.trip.Trip;
import com.rocketseat.planner.dto.TripRequestPayloadDTO;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.response.TripCreateResponse;

@Service
public class TripService {
	
	@Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public TripCreateResponse createTrip(TripRequestPayloadDTO payload) {
        Trip newTrip = new Trip(payload);
        
        tripRepository.save(newTrip);

        participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip);

        return new TripCreateResponse(newTrip.getId());
    }
    
    public ResponseEntity<Trip> getTripDetails(UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    public ResponseEntity<Trip> updateTrip(UUID id, TripRequestPayloadDTO payload) {
        Optional<Trip> tripOptional = tripRepository.findById(id);

        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            trip.setEndsAt(LocalDateTime.parse(payload.ends_at(), FORMATTER));
            trip.setStartsAt(LocalDateTime.parse(payload.starts_at(), FORMATTER));
            trip.setDestination(payload.destination());

            tripRepository.save(trip);

            return ResponseEntity.ok(trip);
        }

        return ResponseEntity.notFound().build();
    }
    
    public ResponseEntity<Trip> confirmTrip(UUID id) {
        Optional<Trip> tripOptional = tripRepository.findById(id);

        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();
            trip.setConfirmed(true);
            tripRepository.save(trip);
            
            participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(trip);
        }

        return ResponseEntity.notFound().build();
    }
    
    
}