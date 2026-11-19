package ma.youcode.rideflow.feature.trip.repository;

import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaypointRepository extends JpaRepository<Waypoint, Long> {
}
