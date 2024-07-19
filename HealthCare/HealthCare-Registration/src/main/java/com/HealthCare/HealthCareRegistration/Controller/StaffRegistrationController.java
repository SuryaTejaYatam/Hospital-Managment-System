package com.HealthCare.HealthCareRegistration.Controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCareRegistration.Entity.StaffRegistration;
import com.HealthCare.HealthCareRegistration.Service.Staff.StaffRegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/registration")
@Slf4j
public class StaffRegistrationController {
    
    @Autowired
    private StaffRegistrationService service;

    @PostMapping("/staffRegistration")
    public StaffRegistration staffRegistration(@RequestBody StaffRegistration staffRegistration){

        log.info("Sending the Deatils to Service Layer");
        StaffRegistration staffRegistration2= service.staffRegistration(staffRegistration);

        log.info("Returing the saved Details");
        return staffRegistration2;
    }


    @GetMapping("/staffLogin/{email}/{password}")
    public StaffRegistration staffLogin(@PathVariable String email,@PathVariable String password){
        return service.staffLogin(email,password);
    }    
}
