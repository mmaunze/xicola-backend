package mz.co.mefemasys.xicola.backend.utils.exceptions;

import java.util.logging.Logger;

public class ResourceNotFoundException extends RuntimeException {

    private static final Logger LOG = Logger.getLogger(ResourceNotFoundException.class.getName());

    public ResourceNotFoundException(String message) {
        super(message);

    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);

    }
}
