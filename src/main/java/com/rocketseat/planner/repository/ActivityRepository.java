package com.rocketseat.planner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketseat.planner.domain.activity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
	
	List<Activity> findByTripId(UUID tripId);

}
