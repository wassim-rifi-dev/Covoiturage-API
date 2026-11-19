package ma.youcode.covoiturage.feature.car.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarSummaryResponseDTO(
    Long id,

    String brand,

    String model,

    @JsonProperty("license_plate")
    String licensePlate,

    String color,

    @JsonProperty("passenger_capacity")
    int passengerCapacity
) {}
