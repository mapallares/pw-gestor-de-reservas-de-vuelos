package com.pw.gestorreservasvuelos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
