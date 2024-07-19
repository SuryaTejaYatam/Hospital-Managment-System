package com.HealthCare.HealthCarePaymentService.Service.OtherServices;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;

public interface PaymentServiceForPatients {

    void placingThePatinetDataTothePaymentTable(String patientId, Payment payment);


    
}
