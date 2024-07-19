package com.HealthCare.HealthCarePaymentService.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentModel {

    private BigDecimal amountPaying;
    private BigDecimal remaingAmount;
    private String paymentMode;
    private String referenceNumber;
    private LocalDate paymentDate;

}
