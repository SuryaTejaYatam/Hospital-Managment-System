package com.HealthCare.HealthCareDoctor.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.HealthCare.HealthCareDoctor.Model.DoctorRegistrationModel;

@FeignClient(name = "doctorLogin" , url = "http://localhost:8084/registration")
public interface DoctorLoginFeignClient {
    

    @GetMapping("/doctorLogin/{email}/{password}")
    public DoctorRegistrationModel doctorLogin(@PathVariable String email,@PathVariable String password);

    @PostMapping("/doctorRegistration")
    public ResponseEntity<DoctorRegistrationModel> registration(@RequestBody DoctorRegistrationModel doctorRegistration);
}
