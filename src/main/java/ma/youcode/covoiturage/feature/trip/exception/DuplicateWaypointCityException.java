package ma.youcode.covoiturage.feature.trip.exception;

public class DuplicateWaypointCityException extends RuntimeException {
    public DuplicateWaypointCityException(String message) {
        super(message);
    }
}
