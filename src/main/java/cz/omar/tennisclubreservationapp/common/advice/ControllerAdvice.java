package cz.omar.tennisclubreservationapp.common.advice;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.user.service.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ControllerAdvice provides global exception handling for controllers within the application.
 */
@RestControllerAdvice
public class ControllerAdvice {
    /**
     * Handles exceptions of type {@code RepositoryException} by returning the exception's message
     * as the response body with a {@code 400 Bad Request} HTTP status.
     *
     * @param ex the {@code RepositoryException} containing the details of the error
     * @return the error message from the exception
     */
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String repositoryHandler(RepositoryException ex) {
        return ex.getMessage();
    }

    /**
     * Handles exceptions of type {@code EmailAlreadyExistsException} by returning the exception's
     * message as the response body with a {@code 400 Bad Request} HTTP status.
     *
     * @param ex the {@code EmailAlreadyExistsException} containing the details of the error
     * @return the error message from the exception
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String repositoryHandler(EmailAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
