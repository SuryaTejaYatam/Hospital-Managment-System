package com.HealthCare.HealthCarePatientService.Exception;

import lombok.Data;

@Data
public class PatientServiceCustomException extends RuntimeException {

    private String errorCode;

    public PatientServiceCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
