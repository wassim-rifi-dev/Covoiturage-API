package ma.youcode.rideflow.feature.booking.exception;

public class BookingCancellationDeadlineExceededException extends RuntimeException {
    public BookingCancellationDeadlineExceededException(String message) {
        super(message);
    }
}
