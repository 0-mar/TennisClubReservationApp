package cz.omar.tennisclubreservationapp.common.storage;

/**
 * RepositoryException is a runtime exception used to indicate issues
 * occurring in the repository layer of the application.
 */
public class RepositoryException extends RuntimeException {
    /**
     * Accepts a descriptive error message explaining the cause or context of the exception.
     * @param message the error message
     */
    public RepositoryException(String message) {
        super(message);
    }
}
