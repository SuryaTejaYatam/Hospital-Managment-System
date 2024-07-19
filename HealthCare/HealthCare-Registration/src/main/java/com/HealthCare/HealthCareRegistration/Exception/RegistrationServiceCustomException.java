package com.HealthCare.HealthCareRegistration.Exception;

import lombok.Data;

@Data
public class RegistrationServiceCustomException extends RuntimeException{
    
    private String errorCode;

    public RegistrationServiceCustomException(String message,String errorCode){
        
        super(message);
        this.errorCode=errorCode;
    }
}
