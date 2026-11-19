package ma.youcode.rideflow.feature.booking.repository;

import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
