package com.HealthCare.HealthCarePatientService.Controller.OtherController;

import java.time.LocalDate;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.DoctorAssign;
import com.HealthCare.HealthCarePatientService.Service.OtherServices.Staff.PatientServiceForStaff;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patient")
@Slf4j
public class StaffControlledMethodsOnPatientTable {

    @Autowired
    private PatientServiceForStaff patientServiceForStaff;

    // method used by the staff
    @PutMapping("/cancelAppointment/{problem}/{appointmentDate}")
    public ResponseEntity<String> cancelAppointment(@RequestParam String patientId, @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("sending the appoinmet deatils to the Service layer");
        String result = patientServiceForStaff.cancelAppointment(patientId, problem, appointmentDate);
        if (result == "Cancelled") {

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(result);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Appointment dosenot Canceled");

    }

    // used by the staff
    @PutMapping("/assignDoctorToThePatient/{patientId}/{problem}/{appointmentDate}")
    public void assignDoctorToThePatient(@RequestBody DoctorAssign doctorAssign, @PathVariable("patientId") String patientId,
            @PathVariable("problem") String problem, @PathVariable("appointmentDate") LocalDate appointmentDate) {

        log.info("Sending it to the PatientService layer");
        patientServiceForStaff.assignDoctorToThePatient(doctorAssign, patientId, problem, appointmentDate);
    }

    // used by staff
    @PutMapping("/placingTotalAmountOfPatient/{patientId}/{problem}/{appointmentDate}")
    public void placingTotalAmountOfPatient(@RequestBody BillingModel billingModel,
            @PathVariable String patientId,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate){

        log.info("Sending it to the PatientService layer");
        patientServiceForStaff.placingTotalAmountOfPatient(billingModel, patientId, problem,
                appointmentDate);
    }

    // used by the staff
    @PutMapping("/checkingTheRemaingAumontAfterInsurance/{patientId}/{problem}/{appointmentDate}")
    public void checkingTheRemaingAumontAfterInsurance(@RequestBody BillingModel billingModel,
            @PathVariable String patientId,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate) {

        log.info("sending to service layer");
        patientServiceForStaff.checkingTheRemaingAumontAfterInsurance(billingModel,
                patientId, problem,
                appointmentDate);
    }

}
