package com.HealthCare.HealthCareDoctor.Feign;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "staffClientForStatus", url = "http://localhost:8082/staff")
public interface StaffFeignClient {

    @PutMapping("/doctorTreatmentCompletedStatus")
    public void doctorTreatmentCompletedStatus(
            @RequestParam String patientId, @RequestParam String problem, @RequestParam LocalDate appointmentDate);
}
