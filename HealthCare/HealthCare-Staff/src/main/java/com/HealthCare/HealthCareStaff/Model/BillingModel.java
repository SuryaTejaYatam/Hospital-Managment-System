package com.HealthCare.HealthCareStaff.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.HealthCare.HealthCareStaff.Entity.ClaimStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BillingModel {
    
    private String firstName;
    private String lastName;
    private String problem;
    private LocalDate appointmentDate;
    private String doctorName;

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
