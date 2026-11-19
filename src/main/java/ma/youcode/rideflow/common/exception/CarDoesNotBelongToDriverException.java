package ma.youcode.rideflow.common.exception;

public class CarDoesNotBelongToDriverException extends RuntimeException {
    public CarDoesNotBelongToDriverException(String message) {
        super(message);
    }
}
