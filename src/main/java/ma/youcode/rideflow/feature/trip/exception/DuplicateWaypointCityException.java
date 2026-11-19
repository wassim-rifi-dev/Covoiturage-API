package ma.youcode.rideflow.feature.trip.exception;

public class DuplicateWaypointCityException extends RuntimeException {
    public DuplicateWaypointCityException(String message) {
        super(message);
    }
}
