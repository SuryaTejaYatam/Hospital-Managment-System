package com.HealthCare.HealthCareStaff.Controller.OtherController;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.PatientModel;
import com.HealthCare.HealthCareStaff.Service.StaffService;
import com.HealthCare.HealthCareStaff.Service.OtherService.Patient.StaffServicesForPatients;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/staff")
@Slf4j
public class PatientsControlleMethods {

    @Autowired
    private StaffServicesForPatients staffServicesForPatients;

    // used by the patient
    @PostMapping("/saveingApppointmentDeatils")
    public void saveingApppointmentDeatils(@RequestBody PatientModel patient) {

        log.info("Saving the Patient Deatils which are come from Patient" + patient);
        staffServicesForPatients.saveingApppointmentDeatils(patient);
    }

    // used by the patient
    @PutMapping("/updatingTheDeatils")
    public void updatingTheDeatils(@RequestParam String patientId, @RequestBody PatientModel patientModel,
            @RequestParam String problem) {

        log.info("Updating  the Patient Deatils which are come from Patient" + patientModel);
        staffServicesForPatients.updatingTheDeatils(patientId, patientModel, problem);
    }

    // method used by the patient
    @PutMapping("/PatientCancelTheAppointment/{patientId}/{problem}/{appointmentDate}")
    public void PatientCancelTheAppointment(@PathVariable String patientId, @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        staffServicesForPatients.cancelingTheAppointmnet(patientId, problem, appointmentDate);
    }

    // used by the patient
    @PutMapping("/placingInsuranceDetails/{patientId}/{problem}/{appointmentDate}")
    public void placingInsuranceDetails(@RequestBody BillingModel billingModel,
            @PathVariable String patientId,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate) {
        log.info("Sending it to the staffService layer");
        staffServicesForPatients.placingInsuranceDetails(billingModel, patientId, problem, appointmentDate);
    }

}
