package com.HealthCare.HealthCarePaymentService.Exception;

import lombok.Data;

@Data
public class PaymentServiceCustomException extends RuntimeException {

    private String errorCode;

    public PaymentServiceCustomException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}
