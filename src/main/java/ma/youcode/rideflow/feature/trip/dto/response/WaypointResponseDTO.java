package ma.youcode.rideflow.feature.trip.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WaypointResponseDTO(
        Long id,

        String city,

        int order,

        @JsonProperty("passage_time")
        LocalDateTime passageTime,

        @JsonProperty("price_from_start")
        BigDecimal priceFromStart
) {}
