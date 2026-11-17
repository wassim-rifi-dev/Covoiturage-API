package ma.youcode.rideflow.feature.car.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CreateCarRequestDTO(
        @NotBlank(message = "Brand is required")
        @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
        String brand,

        @NotBlank(message = "Model is required")
        @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
        String model,

        @JsonProperty("license_plate")
        @NotBlank(message = "License plate is required")
        @Size(max = 20, message = "License plate must not exceed 20 characters")
        String licensePlate,

        @NotBlank(message = "Color is required")
        @Size(max = 30, message = "Color must not exceed 30 characters")
        String color,

        @JsonProperty("passenger_capacity")
        @Min(value = 1, message = "Capacity must be at least 1 passenger")
        @Max(value = 9, message = "Capacity must not exceed 9 passengers")
        int passengerCapacity
) {}
