package ma.youcode.rideflow.feature.trip.repository;

import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
