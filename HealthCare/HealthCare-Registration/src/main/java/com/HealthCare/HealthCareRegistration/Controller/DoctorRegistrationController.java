package com.HealthCare.HealthCareRegistration.Controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCareRegistration.Entity.DoctorRegistration;
import com.HealthCare.HealthCareRegistration.Service.Doctor.DoctorRegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/registration")
@Slf4j
public class DoctorRegistrationController {
    
    @Autowired
    private DoctorRegistrationService doctorRegistrationService;

    @PostMapping("/doctorRegistration")
    public ResponseEntity<DoctorRegistration> registration(@RequestBody DoctorRegistration doctorRegistration){

        log.info("Sending the info to the Service layer");
        DoctorRegistration doctorRegistration2= doctorRegistrationService.registration(doctorRegistration);

        log.info("Returing the Saved info ");
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(doctorRegistration2);

    }

    //used by the doctor service 
    @GetMapping("/doctorLogin/{email}/{password}")
    public DoctorRegistration doctorLogin(@PathVariable String email,@PathVariable String password){
        return doctorRegistrationService.doctorLogin(email,password);
    }

//     @GetMapping("/getDoctorDetails")
//     public List<DoctorRegistration> getAllDoctorDetails(){
//         return doctorRegistrationService.getAllDoctorDetails();
//     }

       @GetMapping("/getDoctorDetails")
       public List<DoctorRegistration> getDoctorDetails(){
        return doctorRegistrationService.getDoctorDetails();
       }
}
