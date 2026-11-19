package ma.youcode.covoiturage.feature.trip.exception;

public class InvalidWaypointPriceException extends RuntimeException {
    public InvalidWaypointPriceException(String message) {
        super(message);
    }
}
