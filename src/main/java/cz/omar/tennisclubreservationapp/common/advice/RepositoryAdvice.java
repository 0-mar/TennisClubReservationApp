package cz.omar.tennisclubreservationapp.common.advice;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RepositoryAdvice {
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String repositoryHandler(RepositoryException ex) {
        return "Could not fetch the data";
    }
}
