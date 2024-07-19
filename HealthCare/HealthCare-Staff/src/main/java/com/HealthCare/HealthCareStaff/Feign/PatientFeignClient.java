package com.HealthCare.HealthCareStaff.Feign;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.DoctorAssign;
import com.HealthCare.HealthCareStaff.Model.PatientModel;

@FeignClient(name = "Patient", url = "http://localhost:8083/patient")
public interface PatientFeignClient {

        @GetMapping("/getAllAppointent")
        public List<PatientModel> getAllAppointment();

        @GetMapping("/getAllCancelAppointent")
        public List<PatientModel> getAllCancelAppointent();

        @PutMapping("/assignDoctorToThePatient/{patientId}/{problem}/{appointmentDate}")
        public void assignDoctorToThePatient(@RequestBody DoctorAssign doctorAssign, @PathVariable String patientId,
                        @PathVariable String problem,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);

        @PutMapping("/cancelAppointment/{problem}/{appointmentDate}")
        public ResponseEntity<String> cancelAppointment(@RequestParam String patientId, @PathVariable String problem,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);

        @PutMapping("/placingTotalAmountOfPatient/{patientId}/{problem}/{appointmentDate}")
        public void placingTotalAmountOfPatient(@RequestBody BillingModel billingModel,
                        @PathVariable String patientId,
                        @PathVariable String problem,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);

        @PutMapping("/checkingTheRemaingAumontAfterInsurance/{patientId}/{problem}/{appointmentDate}")
        public void checkingTheRemaingAumontAfterInsurance(@RequestBody BillingModel billingModel,
                        @PathVariable String patientId,
                        @PathVariable String problem,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);
}
