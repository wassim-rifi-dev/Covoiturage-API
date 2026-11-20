package ma.youcode.covoiturage.feature.trip.exception;

public class CarAlreadyHasTripInTimeRangeException extends RuntimeException {
    public CarAlreadyHasTripInTimeRangeException(String message) {
        super(message);
    }
}
