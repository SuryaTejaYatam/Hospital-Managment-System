package com.HealthCare.HealthCareStaff.Exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorResponse {

    private String errorMessage;
    private String errorCode;
}
