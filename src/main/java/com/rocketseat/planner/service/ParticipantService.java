package com.rocketseat.planner.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.domain.participant.Participant;
import com.rocketseat.planner.domain.trip.Trip;
import com.rocketseat.planner.dto.ParticipantRequestPayload;
import com.rocketseat.planner.repository.ParticipantRepository;
import com.rocketseat.planner.response.ParticipantCreateResponse;


@Service
public class ParticipantService {
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	
	
	
	public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
		List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
		
		
		
		this.participantRepository.saveAll(participants);
		
		System.out.println(participants.get(0).getId());
	}
	
	public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip) {
		Participant newParticipant = new Participant(email, trip);
		this.participantRepository.save(newParticipant);
		
		return new ParticipantCreateResponse(newParticipant.getId());
	}
	
	public void triggerConfirmationEmailToParticipants(UUID tripId) {}
	

	public void triggerConfirmationEmailToParticipant(String email) {}
	
	 public List<ParticipantRequestPayload> getAllParticipantsFromEvent(UUID tripId) {
	        return participantRepository.findByTripId(tripId).stream()
	            .map(participant -> new ParticipantRequestPayload(
	                participant.getId(),
	                participant.getName(),
	                participant.getEmail(),
	                participant.isConfirmed()
	            ))
	            .collect(Collectors.toList());
	    }
	
	
	
}
