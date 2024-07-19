package com.HealthCare.HealthCareDoctor.Feign;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.HealthCare.HealthCareDoctor.Model.MedicalRecordMode;
import com.HealthCare.HealthCareDoctor.Model.TeleconsultationModel;

@FeignClient(name = "TeleconsultationFeignClient", url = "http://localhost:8083/patient")
public interface PatientTeleconsultationFeignClient {

    @PutMapping("/assignTheUrl/{patinetId}/{problem}/{appointmentDate}")
    public void assignTheUrl(@RequestBody TeleconsultationModel teleconsultationModel, @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);

    @PutMapping("/treatmentStatus/{patinetId}/{problem}/{appointmentDate}")
    public void treatmentStatus(@RequestBody TeleconsultationModel teleconsultationModel, @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);


            @PutMapping("/medicalRecord/{patinetId}/{problem}/{appointmentDate}")
            public void MedicalRecord(@RequestBody MedicalRecordMode medicalRecordModel,
            @PathVariable String patinetId,
            @PathVariable String problem,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate appointmentDate);
}
