package ma.youcode.covoiturage.common.exception;

public class TripNotBookableException extends RuntimeException {
    public TripNotBookableException(String message) {
        super(message);
    }
}
