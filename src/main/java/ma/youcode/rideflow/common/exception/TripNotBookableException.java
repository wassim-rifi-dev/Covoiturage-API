package ma.youcode.rideflow.common.exception;

public class TripNotBookableException extends RuntimeException {
    public TripNotBookableException(String message) {
        super(message);
    }
}
