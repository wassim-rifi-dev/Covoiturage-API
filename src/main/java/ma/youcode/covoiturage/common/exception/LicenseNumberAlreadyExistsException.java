package ma.youcode.covoiturage.common.exception;

public class LicenseNumberAlreadyExistsException extends RuntimeException {
    public LicenseNumberAlreadyExistsException(String message) {
        super(message);
    }
}
