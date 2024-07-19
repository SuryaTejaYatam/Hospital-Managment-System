package com.HealthCare.HealthCarePatientService.FeignClient;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name =  "DoctorClient" , url = "http://localhost:8081/doctor")
public interface DoctorFeignClient {
    
    @PutMapping("/cancelingTheAppointmnetByTheStaffOrByThePatinet/{patientId}/{problem}/{appointmentDate}")
    public void cancelingTheAppointmnetByTheStaff(@PathVariable String patientId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate);
}
