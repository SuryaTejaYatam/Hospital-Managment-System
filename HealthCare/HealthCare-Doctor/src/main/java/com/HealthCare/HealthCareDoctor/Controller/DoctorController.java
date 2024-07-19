package com.HealthCare.HealthCareDoctor.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCareDoctor.Entity.Doctor;

import com.HealthCare.HealthCareDoctor.Feign.DoctorLoginFeignClient;
import com.HealthCare.HealthCareDoctor.Feign.PatientTeleconsultationFeignClient;
import com.HealthCare.HealthCareDoctor.Feign.StaffFeignClient;
import com.HealthCare.HealthCareDoctor.Model.DoctorRegistrationModel;
import com.HealthCare.HealthCareDoctor.Model.MedicalRecordMode;
import com.HealthCare.HealthCareDoctor.Model.Patient;
import com.HealthCare.HealthCareDoctor.Model.TeleconsultationModel;
import com.HealthCare.HealthCareDoctor.Service.DoctorSrevice;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    @Autowired
    private DoctorLoginFeignClient feignClient;

    @Autowired
    private DoctorSrevice doctorSrevice;

    @Autowired
    private PatientTeleconsultationFeignClient patientTeleconsultationFeignClient;

    @Autowired
    private StaffFeignClient staffFeignClient;

    private DoctorRegistrationModel doctorRegistrationModel;

    // used by the doctor
    @PostMapping("/doctorRegistration")
    public ResponseEntity<String> doctorRegistration(
            @RequestBody DoctorRegistrationModel doctorRegistrationModel) {

        feignClient.registration(doctorRegistrationModel);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body("Details are Saved");

    }

    // used by the doctor
    @GetMapping("/doctorLogin/{email}/{password}")
    public ResponseEntity<String> doctorLogin(@PathVariable String email,
            @PathVariable String password) {

        doctorRegistrationModel = feignClient.doctorLogin(email, password);
        if (Objects.nonNull(doctorRegistrationModel)) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Login");
        }

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Failed");
    }

    // used by the doctor
    @GetMapping("/getAllPatients")
    public ResponseEntity<List<Patient>> getAllPatients() {

        log.info("sending the patient info th the service layer");
        List<Patient> patients = doctorSrevice.getAllPatients(doctorRegistrationModel.getDoctorId());
        log.info("Checking whethere it patient are ptrsent are not");
        if (Objects.nonNull(patients)) {
            log.info("returing the patients");
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patients);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);
    }

    // used by the doctor
    @GetMapping("/sortPatientByTodayDate")
    public ResponseEntity<List<Patient>> sortPatientByTodayDate() {

        List<Patient> patinets = doctorSrevice.sortPatientByTodayDate(doctorRegistrationModel.getDoctorId());
        if (Objects.nonNull(patinets)) {
            return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(patinets);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);
    }

    // used by the doctor
    // send the consultationUrl
    @PutMapping("/sendingTheUrl/{patinetId}/{problem}/{appointmentDate}")
    public ResponseEntity<String> sendingTheUrl(@RequestBody TeleconsultationModel teleconsultationModel,
            @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending the deayils to the service layer");
        String result = doctorSrevice.sendingTheUrl(teleconsultationModel, patinetId, problem, appointmentDate);
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(result);

    }

    // used by the doctor
    //sends diagnosis,medications,treatmentPlans,medicalHistory
    @PutMapping("/medicalRecords/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<Doctor> medicalRecords(@RequestBody MedicalRecordMode medicalRecordModel,
            @PathVariable String patientId, @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        Doctor patinet = doctorSrevice.medicalRecords(medicalRecordModel, patientId, problem, appointmentDate);
        if (Objects.nonNull(patinet)) {

            log.info("Sending it to the patient service");
            patientTeleconsultationFeignClient.MedicalRecord(medicalRecordModel, patientId, problem, appointmentDate);
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patinet);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);
    }

    // used by the doctor
    //send the treatmentstatus
    @PutMapping("/treatmentStatus/{patinetId}/{problem}/{appointmentDate}")
    public ResponseEntity<String> treatmentStatus(@RequestBody TeleconsultationModel teleconsultationModel,
            @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending the deayils to the service layer");
        String result = doctorSrevice.treatmentStatus(teleconsultationModel, patinetId, problem, appointmentDate);
        if (teleconsultationModel.getTreatmentstatus().equals("Completed")) {
            log.info("Sending the Completed status to the staff module");

            staffFeignClient.doctorTreatmentCompletedStatus(patinetId, problem, appointmentDate);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(result);

    }



}
