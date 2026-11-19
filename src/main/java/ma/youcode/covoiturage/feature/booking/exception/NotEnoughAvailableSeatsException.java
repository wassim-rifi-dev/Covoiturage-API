package ma.youcode.covoiturage.feature.booking.exception;

public class NotEnoughAvailableSeatsException extends RuntimeException {
    public NotEnoughAvailableSeatsException(String message) {
        super(message);
    }
}
