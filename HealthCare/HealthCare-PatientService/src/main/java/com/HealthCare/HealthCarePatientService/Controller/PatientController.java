package com.HealthCare.HealthCarePatientService.Controller;

import java.time.LocalDate;
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
import com.HealthCare.HealthCarePatientService.Entity.Patient;
import com.HealthCare.HealthCarePatientService.FeignClient.DoctorFeignClient;
import com.HealthCare.HealthCarePatientService.FeignClient.PatientLoginFeignClient;
import com.HealthCare.HealthCarePatientService.FeignClient.PaymentFeignClient;
import com.HealthCare.HealthCarePatientService.FeignClient.StaffFeignClient;
import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.DoctorAssign;
import com.HealthCare.HealthCarePatientService.Model.MedicalRecordModel;
import com.HealthCare.HealthCarePatientService.Model.PatientModel;
import com.HealthCare.HealthCarePatientService.Model.PatientResgistration;
import com.HealthCare.HealthCarePatientService.Service.PatientService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientLoginFeignClient feignClient;

    @Autowired
    private StaffFeignClient staffFeignClient;

    @Autowired
    private DoctorFeignClient doctorFeignClient;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    private PatientResgistration patientrResgistration;

    // used by the patient
    // give the all the feilds present in the PatientResgistration except patientId
    @PostMapping("/patientRegistration")
    public ResponseEntity<String> patientRegistration(@RequestBody PatientResgistration patientRegistration) {

        log.info("Usiing the feignClient");
        patientrResgistration = feignClient.patientRegistration(patientRegistration);
        if (Objects.nonNull(patientrResgistration)) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Details are saved");
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Failed");
    }

    // used by the patient
    @GetMapping("/patientLogin/{email}/{password}")
    public ResponseEntity<String> patientLogin(@PathVariable String email,
            @PathVariable String password) {

        log.info("Usiing the feignClient");
        patientrResgistration = feignClient.PatientLogin(email, password);
        if (Objects.nonNull(patientrResgistration)) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Login");
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Failed");
    }

    // used by the patient
    @PostMapping("/createAppointment")
    // SENDS problem AND appointmentDate
    public String createAppointment(@RequestBody PatientModel patientModel) {

        if (Objects.nonNull(patientrResgistration)) {
            log.info("sending the appoinmet deatils to the Service layer");
            Patient patient = patientService.createAppointment(patientModel, patientrResgistration);
            if (Objects.nonNull(patient)) {

                log.info("Sending the details to Staff ,tp save the details");
                staffFeignClient.saveingApppointmentDeatils(patient);

                return "Created Appoinment";
            }
            return "ERROR";
        }
        return "ReLogin";
    }

    // used by the patient
    // SENDS appointmentDate
    @GetMapping("/appointmentDetails/{problem}")
    public ResponseEntity<PatientModel> appointmentDetails(@PathVariable String problem) {

        log.info("sending the appoinmet deatils to the Service layer");
        PatientModel patient = patientService.appointmentDetails(patientrResgistration.getPatientId(), problem);

        if (Objects.nonNull(patient)) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patient);
        }

        return null;

    }

    // used by the patient
    // SENDS appointmentDate
    @PutMapping("/updateAppointment/{problem}")
    public ResponseEntity<String> updateAppointment(@RequestBody PatientModel patientModel,
            @PathVariable String problem) {

        log.info("sending the appoinmet deatils to the Service layer");
        Patient patient = patientService.updateAppointment(patientrResgistration.getPatientId(), patientModel,
                problem);

        if (Objects.nonNull(patient)) {

            staffFeignClient.updatingTheDeatils(patientrResgistration.getPatientId(), patientModel,
                    problem);
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Appointment date is updated");
        }

        return null;
    }

    // method used by the Patient
    @PutMapping("/patientCancelTheAppointment/{problem}/{appointmentDate}")
    public ResponseEntity<String> patientCancelTheAppointment(@PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("sending the appoinmet deatils to the Service layer");
        Patient patient = patientService.patientCancelTheAppointment(patientrResgistration.getPatientId(), problem,
                appointmentDate);
        if (Objects.nonNull(patient)) {

            log.info("sending it to staff service ");
            staffFeignClient.PatientCancelTheAppointment(patientrResgistration.getPatientId(), problem,
                    appointmentDate);

                    log.info("Finding the doctor is present or not ");
            if (patient.getDoctorId() != null) {

                log.info("sending it to doctor service ");
                doctorFeignClient.cancelingTheAppointmnetByTheStaff(patientrResgistration.getPatientId(), problem,
                        appointmentDate);
            }

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Cancelled");
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Appointment dosenot Canceled");

    }

    // used by the patient
    @GetMapping("/checkingTheAssigingDoctor/{problem}/{appointmentDate}")
    public ResponseEntity<DoctorAssign> checkingTheAssigingDoctor(@PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending it to the PatientService layer");
        DoctorAssign patient = patientService.checkingTheAssigingDoctor(patientrResgistration.getPatientId(), problem,
                appointmentDate);
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patient);
    }

    // used by the patient
    @GetMapping("/gettingTheMedicalRecordofpatinet/{problem}/{appointmentDate}")
    public ResponseEntity<MedicalRecordModel> gettingTheMedicalRecordofpatinet(@PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending it to the PatientService layer");
        MedicalRecordModel patient = patientService.gettingTheMedicalRecordofpatinet(
                patientrResgistration.getPatientId(), problem,
                appointmentDate);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patient);
    }

    // used by the patinet
    @GetMapping("/gettingThePatientBillingDetails/{problem}/{appointmentDate}")
    public ResponseEntity<BillingModel> gettingThePatientBillingDetails(@PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        BillingModel patient = patientService.gettingThePatientBillingDetails(patientrResgistration.getPatientId(),
                problem,
                appointmentDate);

        if (Objects.nonNull(patient)) {

            paymentFeignClient.placingThePatinetDataTothePaymentTable(patientrResgistration.getPatientId(), patient);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patient);
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);
    }

    // used by the patient
    // sends insuranceProvider,insurancePolicyNumber
    @PutMapping("/placingInsuranceDetails/{problem}/{appointmentDate}")
    public ResponseEntity<String> placingInsuranceDetails(@RequestBody BillingModel billingModel,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate) {
        log.info("Sending it to the PatientService layer");
        Patient patient = patientService.placingInsuranceDetails(billingModel,
                patientrResgistration.getPatientId(), problem,
                appointmentDate);

        if (Objects.nonNull(patient)) {

            log.info("sending to the staff service");
            staffFeignClient.placingInsuranceDetails(billingModel, patientrResgistration.getPatientId(), problem,
                    appointmentDate);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Deatils are updated");
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Deatils are not updated");
    }

    //used after the full payment
    @PutMapping("/amountFullyPaid/{patientId}/{problem}/{appointmentDate}")
    public void amountFullyPaid(@PathVariable String patientId, @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending to the service layer");
        patientService.amountFullyPaid(patientId, problem, appointmentDate);
    }

   

}
