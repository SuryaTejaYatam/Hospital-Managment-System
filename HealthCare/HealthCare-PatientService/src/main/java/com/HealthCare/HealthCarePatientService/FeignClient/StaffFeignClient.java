package com.HealthCare.HealthCarePatientService.FeignClient;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.HealthCare.HealthCarePatientService.Entity.Patient;
import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.PatientModel;

@FeignClient(name = "StaffClient", url = "http://localhost:8082/staff")
public interface StaffFeignClient {

        @PostMapping("/saveingApppointmentDeatils")
        public void saveingApppointmentDeatils(@RequestBody Patient patient);

        @PutMapping("/updatingTheDeatils")
        public void updatingTheDeatils(@RequestParam String patientId, @RequestBody PatientModel patientModel,
                        @RequestParam String problem);

        @PutMapping("/PatientCancelTheAppointment/{patientId}/{problem}/{appointmentDate}")
        public void PatientCancelTheAppointment(@PathVariable String patientId, @PathVariable String problem,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);

        @PutMapping("/placingInsuranceDetails/{patientId}/{problem}/{appointmentDate}")
        public void placingInsuranceDetails(@RequestBody BillingModel billingModel,
                        @PathVariable String patientId,
                        @PathVariable String problem,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate appointmentDate);
}