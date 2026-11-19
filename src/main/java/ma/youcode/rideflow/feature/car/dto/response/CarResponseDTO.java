package ma.youcode.rideflow.feature.car.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ma.youcode.rideflow.feature.driver.dto.response.DriverResponseDTO;

public record CarResponseDTO(
    Long id,

    String brand,

    String model,

    @JsonProperty("license_plate")
    String licensePlate,

    String color,

    @JsonProperty("passenger_capacity")
    int passengerCapacity,

    DriverResponseDTO driver
) {}
