package ma.youcode.covoiturage.common;

import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.*;
import ma.youcode.covoiturage.feature.booking.exception.*;
import ma.youcode.covoiturage.feature.trip.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException exception) {
        log.warn("400 Bad Request: Validation failed");

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );

        problem.setTitle("Validation failed");

        Map<String , String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                            errors.put(
                                    error.getField(),
                                    error.getDefaultMessage()
                            )
                        );

        problem.setProperty("errors", errors);

        return problem;
    }

    // 404
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException exception) {
        log.warn("404 User not found");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("User not found");

        return problem;
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ProblemDetail handleCarNotFoundException(CarNotFoundException exception) {
        log.warn("404 car not found");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("Car not found.");

        return problem;
    }

    @ExceptionHandler(TripNotFoundException.class)
    public ProblemDetail handleTripNotFoundException(TripNotFoundException exception) {
        log.warn("404 trip not found");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("Trip not found.");

        return problem;
    }

    @ExceptionHandler(WaypointNotFoundException.class)
    public ProblemDetail handleWaypointNotFoundException(WaypointNotFoundException exception) {
        log.warn("404 waypoint not found");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("Waypoint not found.");

        return problem;
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ProblemDetail handleBookingNotFoundException(BookingNotFoundException exception) {
        log.warn("404 booking not found");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problem.setTitle("Booking not found.");

        return problem;
    }

    // 409
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        log.warn("409 Email already exists: user creation rejected");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Email already exists");

        return problem;
    }

    @ExceptionHandler(LicenseNumberAlreadyExistsException.class)
    public ProblemDetail handleLicenseNumberAlreadyExistsException(LicenseNumberAlreadyExistsException exception) {
        log.warn("409 License number already exists: user creation rejected");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("License number` already exists");

        return problem;
    }

    @ExceptionHandler(LicensePlateAlreadyExistsException.class)
    public ProblemDetail handleLicensePlateAlreadyExistsException(LicensePlateAlreadyExistsException exception) {
        log.warn("409 License plate already exist");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("License plate already exist.");

        return problem;
    }

    @ExceptionHandler(CarHasTripException.class)
    public ProblemDetail handleCarHasTripException(CarHasTripException exception) {
        log.warn("409 Car has already a trip.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Car has already a trip.");

        return problem;
    }

    @ExceptionHandler(NotEnoughAvailableSeatsException.class)
    public ProblemDetail handleNotEnoughAvailableSeatsException(NotEnoughAvailableSeatsException exception) {
        log.warn("409 Not enough available seats.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Not enough available seats.");

        return problem;
    }

    @ExceptionHandler(TripNotBookableException.class)
    public ProblemDetail handleTripNotBookableException(TripNotBookableException exception) {
        log.warn("409 Trip not bookable.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Trip not bookable.");

        return problem;
    }

    @ExceptionHandler(InvalidTripStatusException.class)
    public ProblemDetail handleInvalidTripStatusException(InvalidTripStatusException exception) {
        log.warn("409 Invalid trip status.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Invalid trip status.");

        return problem;
    }

    @ExceptionHandler(BookingCancellationDeadlineExceededException.class)
    public ProblemDetail handleBookingCancellationDeadlineExceededException(BookingCancellationDeadlineExceededException exception) {
        log.warn("409 Booking cancellation deadline exceeded.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Booking cancellation deadline exceeded.");

        return problem;
    }

    @ExceptionHandler(BookingAlreadyCancelledException.class)
    public ProblemDetail handleBookingAlreadyCancelledException(BookingAlreadyCancelledException exception) {
        log.warn("409 Booking already cancelled.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Booking already cancelled.");

        return problem;
    }

    // 403
    @ExceptionHandler(DriverNotAuthorizedForTripException.class)
    public ProblemDetail handleDriverNotAuthorizedForTripException(DriverNotAuthorizedForTripException exception) {
        log.warn("403 Driver not authorized for trip.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                exception.getMessage()
        );

        problem.setTitle("Driver not authorized for trip.");

        return problem;
    }

    @ExceptionHandler(CarDoesNotBelongToDriverException.class)
    public ProblemDetail handleCarDoesNotBelongToDriverException(CarDoesNotBelongToDriverException exception) {
        log.warn("403 Car does not belong to driver.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                exception.getMessage()
        );

        problem.setTitle("Car does not belong to driver.");

        return problem;
    }

    @ExceptionHandler(BookingDoesNotBelongToPassengerException.class)
    public ProblemDetail handleBookingDoesNotBelongToPassengerException(BookingDoesNotBelongToPassengerException exception) {
        log.warn("403 Booking does not belong to passenger.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                exception.getMessage()
        );

        problem.setTitle("Booking does not belong to passenger.");

        return problem;
    }

    // 400
    @ExceptionHandler(DepartureDateTimeMustBeInFutureException.class)
    public ProblemDetail handleDepartureDateTimeMustBeInFutureException(DepartureDateTimeMustBeInFutureException exception) {
        log.warn("400 Departure date time must be in future.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Departure date time must be in future.");

        return problem;
    }

    @ExceptionHandler(InsufficientWaypointsException.class)
    public ProblemDetail handleInsufficientWaypointsException(InsufficientWaypointsException exception) {
        log.warn("400 Insufficient waypoints.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Insufficient waypoints.");

        return problem;
    }

    @ExceptionHandler(SeatsExceedCarCapacityException.class)
    public ProblemDetail handleSeatsExceedCarCapacityException(SeatsExceedCarCapacityException exception) {
        log.warn("400 Seats exceed car capacity.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Seats exceed car capacity.");

        return problem;
    }

    @ExceptionHandler(DuplicateWaypointCityException.class)
    public ProblemDetail handleDuplicateWaypointCityException(DuplicateWaypointCityException exception) {
        log.warn("400 Duplicate waypoint city.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Duplicate waypoint city.");

        return problem;
    }

    @ExceptionHandler(InvalidWaypointPriceException.class)
    public ProblemDetail handleInvalidWaypointPriceException(InvalidWaypointPriceException exception) {
        log.warn("400 Invalid waypoint price.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Invalid waypoint price.");

        return problem;
    }

    @ExceptionHandler(InvalidTripTimeException.class)
    public ProblemDetail handleInvalidTripTimeException(InvalidTripTimeException exception) {
        log.warn("400 Invalid trip time.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Invalid trip time.");

        return problem;
    }

    @ExceptionHandler(InvalidTripPriceException.class)
    public ProblemDetail handleInvalidTripPriceException(InvalidTripPriceException exception) {
        log.warn("400 Invalid trip price.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Invalid trip price.");

        return problem;
    }
}
