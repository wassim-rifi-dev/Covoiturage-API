package ma.youcode.rideflow.common;

import lombok.extern.slf4j.Slf4j;
import ma.youcode.rideflow.common.exception.EmailAlreadyExistsException;
import ma.youcode.rideflow.common.exception.LicenseNumberAlreadyExistsException;
import ma.youcode.rideflow.common.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
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
}
