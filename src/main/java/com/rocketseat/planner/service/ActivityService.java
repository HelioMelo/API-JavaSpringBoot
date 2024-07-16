package com.rocketseat.planner.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.domain.activity.Activity;
import com.rocketseat.planner.domain.trip.Trip;
import com.rocketseat.planner.dto.ActivityDTO;
import com.rocketseat.planner.exception.TripNotFoundException;
import com.rocketseat.planner.repository.ActivityRepository;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.response.ActivityResponse;

@Service
public class ActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	 @Autowired
	    private TripRepository tripRepository;
	
//	public ActivityResponse registerActivity(ActivityDTO activityDTO, Trip trip) {
//		Activity newActivity = new Activity(activityDTO.title(),activityDTO.occurs_at(),trip);
//		
//		this.activityRepository.save(newActivity);
//		
//		return new ActivityResponse(newActivity.getId());
//	}
//	
	

	 public ActivityResponse registerActivity(UUID tripId, ActivityDTO activityDTO) {
	        // Verificar se a Trip existe
	        Optional<Trip> trip = tripRepository.findById(tripId);
	        
	        if (trip.isPresent()) {
	            Trip rawTrip = trip.get();
	            
	            // Criar e salvar a nova Activity
	            Activity newActivity = new Activity(activityDTO.title(), activityDTO.occurs_at(), rawTrip);
	            this.activityRepository.save(newActivity);
	            
	            return new ActivityResponse(newActivity.getId());
	        }
	        

	        throw new TripNotFoundException("Trip with ID " + tripId + " not found");
	    }
	
	 public List<ActivityDTO> getAllActivitiesFromId(UUID tripId) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	        return activityRepository.findByTripId(tripId).stream()
	            .map(activity -> new ActivityDTO(
	                activity.getId(),
	                activity.getTitle(),
	                activity.getOccursAt().format(formatter)
	            ))
	            .collect(Collectors.toList());
	    }
	 
	 
	  
	 
}


