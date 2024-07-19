package com.HealthCare.HealthCarePatientService.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.HealthCare.HealthCarePatientService.Model.PatientResgistration;

@FeignClient(name = "PatientLogin" ,url = "http://localhost:8084/registration")
public interface PatientLoginFeignClient {
    
    @GetMapping("/patientLogin/{email}/{password}")
    public PatientResgistration PatientLogin(@PathVariable String email,@PathVariable String password);

    @PostMapping("/patientRegistration")
    public PatientResgistration patientRegistration(@RequestBody PatientResgistration patientRegistration);
}
