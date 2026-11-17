package ma.youcode.rideflow.feature.passenger.repository;

import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>, JpaSpecificationExecutor<Passenger> {
}
