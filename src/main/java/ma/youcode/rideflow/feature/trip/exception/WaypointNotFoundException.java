package ma.youcode.rideflow.feature.trip.exception;

public class WaypointNotFoundException extends RuntimeException {
    public WaypointNotFoundException(String message) {
        super(message);
    }
}
