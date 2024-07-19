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
public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String age;
    private String bloodGroup;
    private String problem;
    private LocalDate appointmentDate;
    private String appointmentStatus;
    private String startingTime;
    private String endingTime;
    private String doctorName;
    private String treatmentstatus;
}
