package mz.co.mefemasys.xicola.backend.service.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * @author Meldo Maunze
 */
@ResponseStatus(value = FORBIDDEN)
public class AuthorizationException extends AccessDeniedException {

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(AuthorizationException.class.getName());

    /**
     * @param message
     */
    public AuthorizationException(String message) {
        super(message);
    }

}
