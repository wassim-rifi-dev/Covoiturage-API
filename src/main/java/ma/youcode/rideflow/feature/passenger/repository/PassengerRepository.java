package ma.youcode.rideflow.feature.passenger.repository;

import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
