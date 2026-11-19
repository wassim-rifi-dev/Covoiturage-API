package ma.youcode.covoiturage.feature.booking.exception;

public class BookingCancellationDeadlineExceededException extends RuntimeException {
    public BookingCancellationDeadlineExceededException(String message) {
        super(message);
    }
}
