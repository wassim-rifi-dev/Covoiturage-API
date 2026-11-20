package ma.youcode.covoiturage.feature.trip.exception;

public class InvalidWaypointOrderException extends RuntimeException {
    public InvalidWaypointOrderException(String message) {
        super(message);
    }
}
