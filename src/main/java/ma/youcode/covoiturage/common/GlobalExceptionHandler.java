package ma.youcode.covoiturage.common;

import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.*;
import ma.youcode.covoiturage.feature.booking.exception.*;
import ma.youcode.covoiturage.feature.trip.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    // 500
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception exception) {
        log.error("Unexpected error occurred", exception);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
        );

        problem.setTitle("Internal Server Error");

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

        problem.setTitle("License number already exists");

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

    @ExceptionHandler(CarAlreadyHasTripInTimeRangeException.class)
    public ProblemDetail handleCarAlreadyHasTripInTimeRangeException(CarAlreadyHasTripInTimeRangeException exception) {
        log.warn("409 Car already has a trip in this time range.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("Car already has a trip in this time range.");

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

    @ExceptionHandler(WaypointNotBelongToTripException.class)
    public ProblemDetail handleWaypointNotBelongToTripException(WaypointNotBelongToTripException exception) {
        log.warn("400 Waypoint does not belong to trip.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Waypoint does not belong to trip.");

        return problem;
    }

    @ExceptionHandler(InvalidWaypointOrderException.class)
    public ProblemDetail handleInvalidWaypointOrderException(InvalidWaypointOrderException exception) {
        log.warn("400 Invalid waypoint order.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problem.setTitle("Invalid waypoint order.");

        return problem;
    }

    // Framework exceptions (otherwise caught by the generic 500 handler)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn("400 Malformed request body.");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Request body is missing or malformed."
        );

        problem.setTitle("Malformed request body.");

        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.warn("400 Invalid parameter type: {}", exception.getName());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid value '" + exception.getValue() + "' for parameter '" + exception.getName() + "'."
        );

        problem.setTitle("Invalid parameter type.");

        return problem;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.warn("400 Missing request parameter: {}", exception.getParameterName());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Required parameter '" + exception.getParameterName() + "' is missing."
        );

        problem.setTitle("Missing request parameter.");

        return problem;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleHandlerMethodValidationException(HandlerMethodValidationException exception) {
        log.warn("400 Parameter validation failed");

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Validation failed");

        Map<String, String> errors = new HashMap<>();

        exception.getParameterValidationResults()
                .forEach(result -> result.getResolvableErrors()
                        .forEach(error -> errors.put(
                                result.getMethodParameter().getParameterName(),
                                error.getDefaultMessage()
                        )));

        problem.setProperty("errors", errors);

        return problem;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.warn("405 Method not allowed: {}", exception.getMethod());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                "HTTP method '" + exception.getMethod() + "' is not supported for this endpoint."
        );

        problem.setTitle("Method not allowed.");

        return problem;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException exception) {
        log.warn("404 Resource not found: {}", exception.getResourcePath());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "No endpoint found for '/" + exception.getResourcePath() + "'."
        );

        problem.setTitle("Resource not found.");

        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.warn("409 Data integrity violation", exception);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "The request conflicts with existing data."
        );

        problem.setTitle("Data integrity violation.");

        return problem;
    }
}
