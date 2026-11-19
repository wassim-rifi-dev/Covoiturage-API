package ma.youcode.rideflow.feature.booking.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CreateBookingRequestDTO(
        @JsonProperty("trip_id")
        @NotNull(message = "Trip id is required")
        @Positive(message = "Trip id must be positive")
        Long tripId,

        @JsonProperty("number_of_seats")
        @Min(value = 1, message = "Number of seats must be at least 1")
        @Max(value = 8, message = "Number of seats must not exceed 8")
        int numberOfSeats,

        @JsonProperty("pickup_point_id")
        @NotNull(message = "Pickup point id is required")
        @Positive(message = "Pickup point id must be positive")
        Long pickupPointId,

        @JsonProperty("dropoff_point_id")
        @NotNull(message = "Dropoff point id is required")
        @Positive(message = "Dropoff point id must be positive")
        Long dropoffPointId
) {}
