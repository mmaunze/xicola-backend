package mz.co.mefemasys.xicola.backend.utils;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String details;
    private static final Logger LOG = Logger.getLogger(ErrorDetails.class.getName());
}
