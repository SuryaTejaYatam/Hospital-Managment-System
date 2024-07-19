package com.HealthCare.HealthCareStaff.Feign;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.HealthCare.HealthCareStaff.Model.DoctorAssign;

@FeignClient(name = "DoctorClient",url = "http://localhost:8081/doctor")
public interface DoctorFeignClient {
    
    @PostMapping("/addingTheAssignDetailsToTheDoctorTable")
    public void addingTheAssignDetailsToTheDoctorTable(@RequestBody DoctorAssign patientModel);

    @PutMapping("/cancelingTheAppointmnetByTheStaff/{patientId}/{problem}/{appointmentDate}")
    public void cancelingTheAppointmnetByTheStaff(@PathVariable String patientId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate);
}
