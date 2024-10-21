package mz.co.mefemasys.xicola.backend.service.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Meldo Maunze
 */
@ResponseStatus(value = NOT_FOUND)
public class DataBindingViolationException extends DataIntegrityViolationException {

    /**
     * @param message
     */
    public DataBindingViolationException(String message) {
        super(message);
    }

}
