package ma.youcode.covoiturage.feature.booking.exception;

public class BookingDoesNotBelongToPassengerException extends RuntimeException {
    public BookingDoesNotBelongToPassengerException(String message) {
        super(message);
    }
}
