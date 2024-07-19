package com.HealthCare.HealthCareDoctor.Controller.OtherControlers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCareDoctor.Model.PatientModel;
import com.HealthCare.HealthCareDoctor.Service.OtherServices.Staff.DoctorServiceForStaff;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctor")
@Slf4j
public class StaffControlMethods {

    @Autowired
    private DoctorServiceForStaff doctorServiceForStaff;

    // used by the staff
    @PostMapping("/addingTheAssignDetailsToTheDoctorTable")
    public void addingTheAssignDetailsToTheDoctorTable(@RequestBody PatientModel patientModel) {
        // sending the patient info th the service layer
        log.info("sending the patient info th the service layer");
        doctorServiceForStaff.assignAndUpdatingDoctorToThePatient(patientModel);
    }

    @PutMapping("/cancelingTheAppointmnetByTheStaffOrByThePatinet/{patientId}/{problem}/{appointmentDate}")
    public void cancelingTheAppointmnetByTheStaff(@PathVariable String patientId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("sending the patient info th the service layer");
        doctorServiceForStaff.cancelingTheAppointmnetByTheStaffOrByThePatinet(patientId,problem,appointmentDate);
    }
}
