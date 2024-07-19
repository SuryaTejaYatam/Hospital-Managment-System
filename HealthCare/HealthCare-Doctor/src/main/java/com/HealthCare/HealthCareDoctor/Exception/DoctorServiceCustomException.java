package com.HealthCare.HealthCareDoctor.Exception;

import lombok.Data;

@Data
public class DoctorServiceCustomException extends RuntimeException {

    private String errorCode;
    public DoctorServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
