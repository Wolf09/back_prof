package com.professional.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Error {
    private String message;
    private String details;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    public Error(String message, String details) {
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public Error(String message) {
        this();
        this.message = message;
    }

    public Error() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters y Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
