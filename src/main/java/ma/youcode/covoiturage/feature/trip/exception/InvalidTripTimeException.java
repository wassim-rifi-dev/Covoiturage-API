package ma.youcode.covoiturage.feature.trip.exception;

public class InvalidTripTimeException extends RuntimeException {
    public InvalidTripTimeException(String message) {
        super(message);
    }
}
