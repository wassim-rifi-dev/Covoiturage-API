package ma.youcode.covoiturage.feature.trip.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WaypointRequestDTO(
        @NotBlank(message = "Waypoint city is required")
        @Size(max = 100, message = "Waypoint city must not exceed 100 characters")
        String city,

        @Min(value = 0, message = "Waypoint order must be at least 0")
        int order,

        @JsonProperty("passage_time")
        @NotNull(message = "Passage time is required")
        @Future(message = "Passage time must be in the future")
        LocalDateTime passageTime,

        @JsonProperty("price_from_start")
        @NotNull(message = "Price from start is required")
        @DecimalMin(value = "0.0", message = "Price from start must be positive or zero")
        @Digits(integer = 8, fraction = 2, message = "Price must have at most 8 digits and 2 decimals")
        BigDecimal priceFromStart
) {}
