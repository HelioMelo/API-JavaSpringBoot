package com.rocketseat.planner.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.planner.domain.trip.Trip;
import com.rocketseat.planner.dto.ActivityDTO;
import com.rocketseat.planner.dto.LinkDTO;
import com.rocketseat.planner.dto.ParticipantRequestPayload;
import com.rocketseat.planner.dto.TripRequestPayloadDTO;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.response.ActivityResponse;
import com.rocketseat.planner.response.LinkResponse;
import com.rocketseat.planner.response.ParticipantCreateResponse;
import com.rocketseat.planner.response.TripCreateResponse;
import com.rocketseat.planner.service.ActivityService;
import com.rocketseat.planner.service.LinkService;
import com.rocketseat.planner.service.ParticipantService;
import com.rocketseat.planner.service.TripService;


@RestController
@RequestMapping("/trips")
public class TripController {
	

	@Autowired
	private TripRepository tripRepository;
    
    @Autowired
    private ParticipantService participantService;
    
    @Autowired
    private TripService tripService;
    
    @Autowired
    private ActivityService activityService;
    
    
    @Autowired
    private LinkService linkService;


    
    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayloadDTO payload) {
        TripCreateResponse response = tripService.createTrip(payload);
        return ResponseEntity.ok(response);
    }
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        return tripService.getTripDetails(id);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayloadDTO payload) {
        return tripService.updateTrip(id, payload);
    }
    
    

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        return tripService.confirmTrip(id);
    }
    
    
   
    
    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityDTO activityDTO) {
      ActivityResponse activityResponse = this.activityService.registerActivity(id, activityDTO);
       return ResponseEntity.ok(activityResponse);
    
    }
    
    
    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDTO>> getAllActivities(@PathVariable UUID id ){
    	List<ActivityDTO> activitiesDTOList = this.activityService.getAllActivitiesFromId(id);
    	return ResponseEntity.ok(activitiesDTOList);
    }
    
    
    
    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id ,@RequestBody ParticipantRequestPayload payload) {
    	Optional<Trip> trip = this.tripRepository.findById(id);

    	if(trip.isPresent()) {
    		Trip rawTrip = trip.get();
    		
    		ParticipantCreateResponse participantResponse = this.participantService.registerParticipantToEvent(payload.email(), rawTrip);
    		
    		if(rawTrip.isConfirmed())  this.participantService.triggerConfirmationEmailToParticipant(payload.email());

    		return ResponseEntity.ok(participantResponse);
    	}
    	return ResponseEntity.notFound().build();
    }
  
    
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantRequestPayload>> getAllParticipants(@PathVariable UUID id ){
    	List<ParticipantRequestPayload> participantList = this.participantService.getAllParticipantsFromEvent(id);
    	return ResponseEntity.ok(participantList);
    }
    
    
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id ,@RequestBody LinkDTO linkDTO) {
    	Optional<Trip> trip = this.tripRepository.findById(id);

    	if(trip.isPresent()) {
    		Trip rawTrip = trip.get();
    		
    		LinkResponse linkResponse = this.linkService.registerLink(linkDTO, rawTrip);
    		return ResponseEntity.ok(linkResponse);
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDTO>> getAllLinks(@PathVariable UUID id ){
    	List<LinkDTO> linkDTO = this.linkService.getAllLinkFromId(id);
    	return ResponseEntity.ok(linkDTO);
    }
    
    
    
    
   
}
