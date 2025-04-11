package com.openclassrooms.safetyNet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FireStationAlreadyExistsException extends RuntimeException {
    public FireStationAlreadyExistsException(String message) {
        super(message);
    }
}
