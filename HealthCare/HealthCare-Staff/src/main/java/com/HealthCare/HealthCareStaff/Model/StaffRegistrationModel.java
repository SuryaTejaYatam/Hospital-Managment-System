package com.HealthCare.HealthCareStaff.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StaffRegistrationModel {
    
    private String staffId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String reTypePassword;

    private String phoneNumber;

}
