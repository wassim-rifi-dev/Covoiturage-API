package ma.youcode.rideflow.feature.booking.repository;

import ma.youcode.rideflow.feature.booking.model.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByPassengerId(Long passengerId, Pageable pageable);

    Page<Booking> findAllByDriverId(Long driverId, Pageable pageable);
}
