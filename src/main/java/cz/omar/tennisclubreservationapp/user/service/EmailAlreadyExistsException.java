package cz.omar.tennisclubreservationapp.user.service;

/**
 * Exception thrown when an attempt is made to create a user account
 * with an email address that already exists in the system.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
