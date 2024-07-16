package com.rocketseat.planner.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketseat.planner.domain.trip.Trip;

public interface TripRepository extends JpaRepository<Trip, UUID> {

}
