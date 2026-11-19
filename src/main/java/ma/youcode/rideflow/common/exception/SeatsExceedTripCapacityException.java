package ma.youcode.rideflow.common.exception;

public class SeatsExceedTripCapacityException extends RuntimeException {
    public SeatsExceedTripCapacityException(String message) {
        super(message);
    }
}
