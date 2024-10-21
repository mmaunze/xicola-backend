package mz.co.mefemasys.xicola.backend.exceptions;

import java.util.logging.Logger;

public class BadRequestException extends RuntimeException {

    private static final Logger LOG = Logger.getLogger(BadRequestException.class.getName());

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
