package ma.youcode.rideflow.feature.trip.exception;

public class DepartureDateTimeMustBeInFutureException extends RuntimeException {
    public DepartureDateTimeMustBeInFutureException(String message) {
        super(message);
    }
}
