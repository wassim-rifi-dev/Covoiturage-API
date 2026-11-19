package ma.youcode.rideflow.feature.trip.repository;

import ma.youcode.rideflow.feature.car.model.entity.Car;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.enums.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsByCarIdAndStatusNotIn(Car car, Collection<TripStatus> status);

    String SEARCH_CONDITIONS = """
            WHERE (:departureCity IS NULL OR LOWER(t.departureCity) LIKE :departureCity)
              AND (:arrivalCity IS NULL OR LOWER(t.arrivalCity) LIKE :arrivalCity)
              AND (:dateFrom IS NULL OR t.departureDateTime >= :dateFrom)
              AND (:dateTo IS NULL OR t.departureDateTime < :dateTo)
              AND (:maxPrice IS NULL OR t.price <= :maxPrice)
              AND (:minSeats IS NULL OR t.proposedSeats >= :minSeats)
              AND (:waypointCity IS NULL OR EXISTS (
                    SELECT w.id FROM Waypoint w
                    WHERE w.trip = t AND LOWER(w.city) LIKE :waypointCity
              ))
            """;

    @Query(
            value = "SELECT t FROM Trip t " + SEARCH_CONDITIONS,
            countQuery = "SELECT COUNT(t) FROM Trip t " + SEARCH_CONDITIONS
    )
    Page<Trip> searchTrips(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minSeats") Integer minSeats,
            @Param("waypointCity") String waypointCity,
            Pageable pageable
    );
}
