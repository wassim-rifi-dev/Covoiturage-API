package ma.youcode.rideflow.feature.booking.repository;

import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import ma.youcode.rideflow.feature.passenger.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existByPassenger(Passenger passenger);
}
