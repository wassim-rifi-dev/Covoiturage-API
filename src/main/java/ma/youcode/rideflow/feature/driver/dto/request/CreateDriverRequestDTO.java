package ma.youcode.rideflow.feature.driver.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateDriverRequestDTO(
        @JsonProperty("first_name")
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @JsonProperty("last_name")
        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^(?:\\+212|06)[0-9]{8}$", message = "Phone must start with +212 or 06, followed by exactly 8 digits")
        String phone,

        @JsonProperty("license_number")
        @NotBlank(message = "License number is required")
        @Size(max = 30, message = "License number must not exceed 30 characters")
        String licenseNumber
) {}
