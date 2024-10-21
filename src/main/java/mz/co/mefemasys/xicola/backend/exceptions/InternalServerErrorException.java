package mz.co.mefemasys.xicola.backend.exceptions;

import java.util.logging.Logger;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
    private static final Logger LOG = Logger.getLogger(InternalServerErrorException.class.getName());
}
