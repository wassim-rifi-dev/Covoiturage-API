package ma.youcode.covoiturage.feature.trip.exception;

public class InvalidTripStatusException extends RuntimeException {
    public InvalidTripStatusException(String message) {
        super(message);
    }
}
