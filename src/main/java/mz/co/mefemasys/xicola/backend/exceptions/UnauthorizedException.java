package mz.co.mefemasys.xicola.backend.exceptions;

import java.util.logging.Logger;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
    private static final Logger LOG = Logger.getLogger(UnauthorizedException.class.getName());
}
