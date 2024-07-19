package com.HealthCare.HealthCarePatientService.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.HealthCare.HealthCarePatientService.Model.BillingModel;

@FeignClient(name = "PayemntClient", url = "http://localhost:8085/payment")
public interface PaymentFeignClient {

    @PostMapping("/placingThePatinetDataTothePaymentTable/{patientId}")
    public void placingThePatinetDataTothePaymentTable(@PathVariable String patientId,
            @RequestBody BillingModel paymentModel);
}
