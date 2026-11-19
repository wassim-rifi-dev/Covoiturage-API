package ma.youcode.covoiturage.feature.trip.exception;

public class WaypointNotFoundException extends RuntimeException {
    public WaypointNotFoundException(String message) {
        super(message);
    }
}
