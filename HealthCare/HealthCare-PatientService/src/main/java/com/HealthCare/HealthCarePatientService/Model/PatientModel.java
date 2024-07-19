package com.HealthCare.HealthCarePatientService.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientModel {
    
    //private String patientId;
    private String firstName;
    private String lastName;
    private String startingTime;
    private String endingTime;
    private String doctorName;


    private String problem;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
}
