package com.HealthCare.HealthCarePatientService.Controller.OtherController;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCarePatientService.Model.MedicalRecordModel;
import com.HealthCare.HealthCarePatientService.Model.TeleconsultationModel;
import com.HealthCare.HealthCarePatientService.Service.PatientService;
import com.HealthCare.HealthCarePatientService.Service.OtherServices.Doctor.PatientServiceForTheDoctor;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patient")
@Slf4j
public class DoctorControlledMethods {

    @Autowired
    private PatientServiceForTheDoctor patientServiceForTheDoctor;

    // used by the doctor
    @PutMapping("/assignTheUrl/{patinetId}/{problem}/{appointmentDate}")
    public void assignTheUrl(@RequestBody TeleconsultationModel teleconsultationModel, @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending it to the PatientService layer");
        patientServiceForTheDoctor.assignTheUrl(teleconsultationModel, patinetId, problem, appointmentDate);

    }

    // used by the doctor
    @PutMapping("/treatmentStatus/{patinetId}/{problem}/{appointmentDate}")
    public void treatmentStatus(@RequestBody TeleconsultationModel teleconsultationModel,
            @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending it to the PatientService layer");
        patientServiceForTheDoctor.treatmentStatus(teleconsultationModel, patinetId, problem, appointmentDate);
    }

    // used by the Doctor
    @PutMapping("/medicalRecord/{patinetId}/{problem}/{appointmentDate}")
    public void MedicalRecord(@RequestBody MedicalRecordModel medicalRecordModel,
            @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending it to the PatientService layer");
        patientServiceForTheDoctor.MedicalRecord(medicalRecordModel, patinetId, problem, appointmentDate);
    }

}
