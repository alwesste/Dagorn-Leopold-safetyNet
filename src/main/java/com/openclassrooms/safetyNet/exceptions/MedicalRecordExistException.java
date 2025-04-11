package com.openclassrooms.safetyNet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MedicalRecordExistException extends RuntimeException {
    public MedicalRecordExistException(String message) {
      super(message);
    }
}
