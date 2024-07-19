package com.HealthCare.HealthCarePatientService.Model;

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
    

    private String firstName;
    private String lastName;
    private String problem;
    private LocalDate appointmentDate;

    private String startingTime;
    private String endingTime;
    private String doctorName;

    private String consultationUrl;
    private String treatmentstatus;
}
