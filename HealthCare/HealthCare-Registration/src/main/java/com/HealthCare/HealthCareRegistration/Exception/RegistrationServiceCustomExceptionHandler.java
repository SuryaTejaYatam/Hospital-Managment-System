package com.HealthCare.HealthCareRegistration.Exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RegistrationServiceCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegistrationServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handlingTheException(RegistrationServiceCustomException exception) {

        ErrorResponse customErrorResponse = ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build();
        log.info("-----------------------------------------------------The Exception is {}", customErrorResponse);
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(customErrorResponse);
    }

}