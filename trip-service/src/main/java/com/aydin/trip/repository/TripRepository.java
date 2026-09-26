package com.aydin.trip.repository;

import com.aydin.trip.entity.Trip;
import com.aydin.trip.entity.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    Optional<Trip> findByCardIdAndStatus(UUID cardId, TripStatus status);
}