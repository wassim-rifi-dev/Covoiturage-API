package ma.youcode.rideflow.feature.driver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DriverResponseDTO(
        Long id,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        String email,

        String phone,

        @JsonProperty("license_number")
        String licenseNumber
) {}
