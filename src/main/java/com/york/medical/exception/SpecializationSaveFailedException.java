package com.york.medical.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SpecializationSaveFailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SpecializationSaveFailedException(String message) {
        super(message);
    }
}
