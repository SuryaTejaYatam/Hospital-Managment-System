package com.HealthCare.HealthCarePatientService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeleconsultationModel {
    
    private String consultationUrl;
    private String treatmentstatus;
}