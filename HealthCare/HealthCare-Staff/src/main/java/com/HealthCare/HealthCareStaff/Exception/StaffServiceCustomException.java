package com.HealthCare.HealthCareStaff.Exception;

import lombok.Data;

@Data
public class StaffServiceCustomException extends RuntimeException {

    private String errorCode;

    public StaffServiceCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
