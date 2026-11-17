package ma.youcode.rideflow.feature.driver.dto.request;

public record DriverSearchCriteria(
    String fullName,
    String email,
    String phone,
    String licenseNumber
) {}
