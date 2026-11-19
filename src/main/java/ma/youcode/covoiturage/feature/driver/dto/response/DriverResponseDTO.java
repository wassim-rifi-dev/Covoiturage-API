package ma.youcode.covoiturage.feature.driver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
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
