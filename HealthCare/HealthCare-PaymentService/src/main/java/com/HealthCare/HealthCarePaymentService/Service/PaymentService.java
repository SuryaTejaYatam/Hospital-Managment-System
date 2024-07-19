package com.HealthCare.HealthCarePaymentService.Service;

import java.time.LocalDate;
import java.util.List;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;
import com.HealthCare.HealthCarePaymentService.Model.PaymentModel;

public interface PaymentService {

    Payment paymenting(PaymentModel paymentModel, String patientId, String problem, LocalDate appointmentDate);

    Payment listOfPayments(String problem, String patientId, LocalDate appointmentDate);
    
}
