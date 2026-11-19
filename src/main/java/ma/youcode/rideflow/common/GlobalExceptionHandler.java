package ma.youcode.rideflow.common;

import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.*;
import ma.youcode.rideflow.feature.booking.exception.BookingNotFoundException;
import ma.youcode.rideflow.feature.trip.exception.TripNotFoundException;
import ma.youcode.rideflow.feature.trip.exception.WaypointNotFoundException;
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

    // 404
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
}
