package mz.co.mefemasys.xicola.backend.utils;

import lombok.AllArgsConstructor;

import lombok.Data;

import java.time.LocalDateTime;

import java.util.logging.Logger;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private static final Logger LOG = Logger.getLogger(ErrorDetails.class.getName());

    private LocalDateTime timestamp;

    private int status;

    private String message;

    private String details;

}
