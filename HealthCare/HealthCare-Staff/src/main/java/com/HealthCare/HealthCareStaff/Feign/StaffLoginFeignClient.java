package com.HealthCare.HealthCareStaff.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.HealthCare.HealthCareStaff.Model.StaffRegistrationModel;



@FeignClient(name = "staffLogin" , url = "http://localhost:8084/registration")
public interface StaffLoginFeignClient {
    
    @GetMapping("/staffLogin/{email}/{password}")
    public StaffRegistrationModel staffLogin(@PathVariable String email,@PathVariable String password);

    @PostMapping("/staffRegistration")
    public StaffRegistrationModel staffRegistration(@RequestBody StaffRegistrationModel staffRegistration);


}
