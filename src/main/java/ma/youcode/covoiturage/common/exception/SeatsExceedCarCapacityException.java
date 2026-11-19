package ma.youcode.covoiturage.common.exception;

public class SeatsExceedCarCapacityException extends RuntimeException {
    public SeatsExceedCarCapacityException(String message) {
        super(message);
    }
}
