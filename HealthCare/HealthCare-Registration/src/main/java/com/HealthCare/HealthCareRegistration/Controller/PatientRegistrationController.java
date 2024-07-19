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

import com.HealthCare.HealthCareRegistration.Entity.PatientRegistration;
import com.HealthCare.HealthCareRegistration.Service.Patient.PatientRegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/registration")
@Slf4j
public class PatientRegistrationController {
    
    @Autowired
    private PatientRegistrationService patientRegistrationService;

    //used by the patient service
    @PostMapping("/patientRegistration")
    public PatientRegistration patientRegistration(@RequestBody PatientRegistration patientRegistration){

        log.info("Sending the Deatils to Service Layer");
        PatientRegistration patientRegistration2= patientRegistrationService.patientRegistration(patientRegistration);

        log.info("Returing the saved Details");
        return patientRegistration2;
    }

    //used by the patient Service
    @GetMapping("/patientLogin/{email}/{password}")
    public PatientRegistration PatientLogin(@PathVariable String email,@PathVariable String password){
        return patientRegistrationService.PatientLogin(email,password);
    }
}
