package com.rocketseat.planner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketseat.planner.domain.link.Link;

public interface LinkRepository extends JpaRepository<Link, UUID> {
	
	List<Link> findByTripId(UUID tripId);

}
