package ma.youcode.covoiturage.feature.trip.exception;

public class InvalidTripPriceException extends RuntimeException {
    public InvalidTripPriceException(String message) {
        super(message);
    }
}
