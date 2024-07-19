package com.HealthCare.HealthCarePatientService.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patientId;
    private String doctorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String age;
    private String bloodGroup;
    private String problem;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
    private LocalDate todayDate;
    private String appointmentStatus;
    private String startingTime;
    private String endingTime;
    private String doctorName;
    private String treatmentstatus;
    private String consultationUrl;

    private String diagnosis;
    private String medications;
    private String treatmentPlans;
    private String medicalHistory;

    // medical billing

    private BigDecimal totalAmount;
    private LocalDate billingDate;

    private String insuranceProvider;
    private String insurancePolicyNumber;
    private BigDecimal insuranceCoverageAmount;
    private BigDecimal amountDueAfterInsurance;

    private LocalDate paymentCompletedDate;
    private String paymentStatus;

    private ClaimStatus claimStatus;
}
