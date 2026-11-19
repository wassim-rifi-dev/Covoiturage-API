package ma.youcode.rideflow.feature.trip.exception;

public class InvalidTripStatusException extends RuntimeException {
    public InvalidTripStatusException(String message) {
        super(message);
    }
}
