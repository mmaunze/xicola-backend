package com.xicola.xicola.service.exceptions;

import static org.springframework.http.HttpStatus.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Meldo Maunze
 */
@ResponseStatus(value = NOT_FOUND)
public class DataBindingViolationException extends DataIntegrityViolationException {

    /**
     *
     * @param message
     */
    public DataBindingViolationException(String message) {
        super(message);
    }

}
