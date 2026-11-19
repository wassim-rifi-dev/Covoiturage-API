package ma.youcode.covoiturage.feature.trip.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ma.youcode.covoiturage.feature.car.dto.response.CarSummaryResponseDTO;
import ma.youcode.covoiturage.feature.driver.dto.response.DriverResponseDTO;
import ma.youcode.covoiturage.feature.trip.model.enums.TripStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TripResponseDTO(
        Long id,

        @JsonProperty("departure_city")
        String departureCity,

        @JsonProperty("arrival_city")
        String arrivalCity,

        @JsonProperty("departure_date_time")
        LocalDateTime departureDateTime,

        @JsonProperty("proposed_seats")
        int proposedSeats,

        TripStatus status,

        DriverResponseDTO driver,

        CarSummaryResponseDTO car,

        List<WaypointResponseDTO> waypoints
) {}
