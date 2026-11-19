package ma.youcode.covoiturage.common.exception;

public class CarDoesNotBelongToDriverException extends RuntimeException {
    public CarDoesNotBelongToDriverException(String message) {
        super(message);
    }
}
