package ma.youcode.covoiturage.feature.trip.exception;

public class InsufficientWaypointsException extends RuntimeException {
    public InsufficientWaypointsException(String message) {
        super(message);
    }
}
