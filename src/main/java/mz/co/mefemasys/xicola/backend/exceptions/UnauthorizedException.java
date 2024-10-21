package mz.co.mefemasys.xicola.backend.exceptions;

import java.util.logging.Logger;

public class UnauthorizedException extends RuntimeException {

    private static final Logger LOG = Logger.getLogger(UnauthorizedException.class.getName());

    public UnauthorizedException(String message) {
        super(message);

    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);

    }
}
