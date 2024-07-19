package com.HealthCare.HealthCarePaymentService.Controller.OtherController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;
import com.HealthCare.HealthCarePaymentService.Service.OtherServices.PaymentServiceForPatients;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PatientController {

    @Autowired
    private PaymentServiceForPatients PaymentServiceForPatients;

    @PostMapping("/placingThePatinetDataTothePaymentTable/{patientId}")
    public void placingThePatinetDataTothePaymentTable(@PathVariable String patientId,
            @RequestBody Payment payment){

        log.info("Sending the details to the Payment service");
        PaymentServiceForPatients.placingThePatinetDataTothePaymentTable(patientId,payment);
    }
}
