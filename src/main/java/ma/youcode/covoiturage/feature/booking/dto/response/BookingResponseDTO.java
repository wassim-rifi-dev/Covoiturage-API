package ma.youcode.covoiturage.feature.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import ma.youcode.covoiturage.feature.booking.model.enums.BookingStatus;
import ma.youcode.covoiturage.feature.passenger.dto.response.PassengerResponseDTO;
import ma.youcode.covoiturage.feature.trip.dto.response.TripResponseDTO;
import ma.youcode.covoiturage.feature.trip.dto.response.WaypointResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record BookingResponseDTO(
        Long id,

        @JsonProperty("number_of_seats")
        int numberOfSeats,

        @JsonProperty("total_amount")
        BigDecimal totalAmount,

        @JsonProperty("booking_date")
        LocalDateTime bookingDate,

        @JsonProperty("status")
        BookingStatus status,

        @JsonProperty("trip")
        TripResponseDTO trip,

        @JsonProperty("passenger")
        PassengerResponseDTO passenger,

        @JsonProperty("pickup_point")
        WaypointResponseDTO pickupPoint,

        @JsonProperty("dropoff_point")
        WaypointResponseDTO dropoffPoint
) {}
