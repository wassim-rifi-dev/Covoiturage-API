package ma.youcode.rideflow.feature.trip.specification;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import ma.youcode.rideflow.feature.trip.model.entity.Trip;
import ma.youcode.rideflow.feature.trip.model.entity.Waypoint;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TripSpecification {

    public TripSpecification() {
    }

    public static Specification<Trip> hasDepartureCity(String departureCity) {
        return (root, query, cb) -> {
            if (departureCity == null || departureCity.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("departureCity")), "%" + departureCity.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Trip> hasArrivalCity(String arrivalCity) {
        return (root, query, cb) -> {
            if (arrivalCity == null || arrivalCity.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("arrivalCity")), "%" + arrivalCity.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Trip> hasDepartureDate(LocalDate departureDate) {
        return (root, query, cb) -> {
            if (departureDate == null) {
                return cb.conjunction();
            }
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get("departureDateTime"), departureDate.atStartOfDay()),
                    cb.lessThan(root.get("departureDateTime"), departureDate.plusDays(1).atStartOfDay())
            );
        };
    }

    public static Specification<Trip> hasMaxPrice(BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    public static Specification<Trip> hasMinSeats(Integer minSeats) {
        return (root, query, cb) -> {
            if (minSeats == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("proposedSeats"), minSeats);
        };
    }

    public static Specification<Trip> hasWaypointCity(String waypointCity) {
        return (root, query, cb) -> {
            if (waypointCity == null || waypointCity.isBlank()) {
                return cb.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Waypoint> waypoint = subquery.from(Waypoint.class);

            subquery.select(waypoint.get("id"))
                    .where(
                            cb.equal(waypoint.get("trip"), root),
                            cb.like(cb.lower(waypoint.get("city")), "%" + waypointCity.trim().toLowerCase() + "%")
                    );

            return cb.exists(subquery);
        };
    }
}
