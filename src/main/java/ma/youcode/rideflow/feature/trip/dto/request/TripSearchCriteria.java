package ma.youcode.rideflow.feature.trip.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TripSearchCriteria(
    String departureCity,
    String arrivalCity,
    LocalDate departureDate,
    BigDecimal maxPrice,
    Integer minSeats,
    String waypointCity
) {}
