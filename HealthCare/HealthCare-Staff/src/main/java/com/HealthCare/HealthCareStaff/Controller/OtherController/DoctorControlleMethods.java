package com.HealthCare.HealthCareStaff.Controller.OtherController;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.HealthCare.HealthCareStaff.Service.OtherService.Doctor.StaffServicesForDoctor;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/staff")
@Slf4j
public class DoctorControlleMethods {

    @Autowired
    private StaffServicesForDoctor staffServicesForDoctor;

    // used by thr doctor
    @PutMapping("/doctorTreatmentCompletedStatus")
    public void doctorTreatmentCompletedStatus(
            @RequestParam String patientId, @RequestParam String problem, @RequestParam LocalDate appointmentDate) {

        log.info("sending to the service layer");
        staffServicesForDoctor.doctorTreatmentCompletedStatus(patientId, problem, appointmentDate);
    }
}
