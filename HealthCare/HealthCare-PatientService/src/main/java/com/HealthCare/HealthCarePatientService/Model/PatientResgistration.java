package com.HealthCare.HealthCarePatientService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientResgistration {
    
    private String patientId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String reTypePassword;
    
    private String phoneNumber;

    private String gender;

    private String age;

    private String bloodGroup;

}
