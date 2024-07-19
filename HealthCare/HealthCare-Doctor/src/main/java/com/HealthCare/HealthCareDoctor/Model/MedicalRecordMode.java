package com.HealthCare.HealthCareDoctor.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicalRecordMode {
    
    private String firstName;
    private String lastName;
    private String problem;
    private LocalDate appointmentDate;
    private String doctorName;
    

    private String diagnosis;
    private String medications;
    private String treatmentPlans;
    private String medicalHistory;
}
