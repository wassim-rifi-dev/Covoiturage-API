package ma.youcode.rideflow.feature.trip.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateTripRequestDTO(
        @JsonProperty("departure_city")
        @NotBlank(message = "Departure city is required")
        @Size(max = 100, message = "Departure city must not exceed 100 characters")
        String departureCity,

        @JsonProperty("arrival_city")
        @NotBlank(message = "Arrival city is required")
        @Size(max = 100, message = "Arrival city must not exceed 100 characters")
        String arrivalCity,

        @JsonProperty("departure_date_time")
        @NotNull(message = "Departure date and time is required")
        @Future(message = "Departure date and time must be in the future")
        LocalDateTime departureDateTime,

        @JsonProperty("arrival_date_time")
        @NotNull(message = "Arrival date and time is required")
        @Future(message = "Arrival date and time must be in the future")
        LocalDateTime arrivalDateTime,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 8, fraction = 2, message = "Price must have at most 8 digits and 2 decimals")
        BigDecimal price,

        @JsonProperty("proposed_seats")
        @Min(value = 1, message = "Proposed seats must be at least 1")
        @Max(value = 8, message = "Proposed seats must not exceed 8")
        int proposedSeats,

        @JsonProperty("car_id")
        @NotNull(message = "Car id is required")
        @Positive(message = "Car id must be positive")
        Long carId,

        @Valid
        List<WaypointRequestDTO> waypoints
) {}
