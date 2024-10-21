package mz.co.mefemasys.xicola.backend.exceptions;

import java.util.logging.Logger;

public class ConflictException extends RuntimeException {

    private static final Logger LOG = Logger.getLogger(ConflictException.class.getName());

    public ConflictException(String message) {
        super(message);

    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);

    }
}
