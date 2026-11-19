package ma.youcode.rideflow.feature.trip.repository;

import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsByCarIdAndStatusNotIn(Car car, Collection<TripStatus> status);
}
