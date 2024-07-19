package com.HealthCare.HealthCarePatientService.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PatientServiceCustomExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheDoctorService(PatientServiceCustomException exception) {
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
