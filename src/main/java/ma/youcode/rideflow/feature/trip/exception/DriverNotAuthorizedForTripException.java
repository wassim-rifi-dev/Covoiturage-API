package ma.youcode.rideflow.feature.trip.exception;

public class DriverNotAuthorizedForTripException extends RuntimeException {
    public DriverNotAuthorizedForTripException(String message) {
        super(message);
    }
}
