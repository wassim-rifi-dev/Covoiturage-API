package ma.youcode.covoiturage.feature.passenger.dto.request;

public record PassengerSearchCriteria(
    String fullName,
    String email,
    String phone
) {}
