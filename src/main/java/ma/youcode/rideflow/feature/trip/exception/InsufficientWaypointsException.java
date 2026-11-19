package ma.youcode.rideflow.feature.trip.exception;

public class InsufficientWaypointsException extends RuntimeException {
    public InsufficientWaypointsException(String message) {
        super(message);
    }
}
