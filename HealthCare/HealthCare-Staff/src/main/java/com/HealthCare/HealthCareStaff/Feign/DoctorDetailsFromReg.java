package com.HealthCare.HealthCareStaff.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.HealthCare.HealthCareStaff.Model.DoctorRegistration;

@FeignClient(name = "DoctorDetails",url = "http://localhost:8084/registration")
public interface DoctorDetailsFromReg {

    @GetMapping("/getDoctorDetails")
    public List<DoctorRegistration> getDoctorDetails();
    
    
}
