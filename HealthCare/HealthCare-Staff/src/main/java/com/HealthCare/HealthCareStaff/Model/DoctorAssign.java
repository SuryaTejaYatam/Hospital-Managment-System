package com.HealthCare.HealthCareStaff.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoctorAssign {
    

    private String patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String age;
    private String bloodGroup;
    private String appointmentStatus;
    private String problem;
    private String treatmentstatus;
    private LocalDate appointmentDate;


    private String doctorId;
    private String startingTime;
    private String endingTime;
    private String doctorName;
}
