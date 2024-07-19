package com.HealthCare.HealthCareDoctor.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoctorRegistrationModel {
    
    private String doctorId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String reTypePassword;
    private String gender;
    private String dob;
    private String experience;
    private String specification;
    private String qualification;
}
