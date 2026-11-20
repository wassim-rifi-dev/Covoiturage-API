package ma.youcode.covoiturage.feature.trip.exception;

public class WaypointNotBelongToTripException extends RuntimeException {
    public WaypointNotBelongToTripException(String message) {
        super(message);
    }
}
