package com.HealthCare.HealthCarePaymentService.FeignClinet;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "patientClientForpayment", url = "http://localhost:8083/patient")
public interface PatinetFeignClient {

    @PutMapping("/amountFullyPaid/{patientId}/{problem}/{appointmentDate}")
    public void amountFullyPaid(@PathVariable String patientId, @PathVariable String problem,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);
}
