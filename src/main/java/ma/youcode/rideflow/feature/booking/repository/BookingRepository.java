package ma.youcode.rideflow.feature.booking.repository;

import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
