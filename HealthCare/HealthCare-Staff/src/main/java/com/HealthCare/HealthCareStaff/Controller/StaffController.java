package com.HealthCare.HealthCareStaff.Controller;

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

import com.HealthCare.HealthCareStaff.Entity.Staff;
import com.HealthCare.HealthCareStaff.Feign.DoctorDetailsFromReg;
import com.HealthCare.HealthCareStaff.Feign.DoctorFeignClient;
import com.HealthCare.HealthCareStaff.Feign.PatientFeignClient;
import com.HealthCare.HealthCareStaff.Feign.StaffLoginFeignClient;
import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.CompletedPatientModel;
import com.HealthCare.HealthCareStaff.Model.DoctorAssign;
import com.HealthCare.HealthCareStaff.Model.DoctorRegistration;
import com.HealthCare.HealthCareStaff.Model.PatientModel;
import com.HealthCare.HealthCareStaff.Model.StaffRegistrationModel;
import com.HealthCare.HealthCareStaff.Service.StaffService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/staff")
@Slf4j
public class StaffController {

    @Autowired
    private StaffLoginFeignClient staffLoginFeignClient;

    @Autowired
    private PatientFeignClient patientFeignClient;

    @Autowired
    private DoctorDetailsFromReg doctorDetailsFromReg;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DoctorFeignClient doctorFeignClient;

    // used by the staff
    @PostMapping("/staffRegistration")
    public String staffRegistration(@RequestBody StaffRegistrationModel staffRegistration) {
        staffLoginFeignClient.staffRegistration(staffRegistration);
        return "Details are saved ";
    }

    // used by the staff
    @GetMapping("/staffLogin/{email}/{password}")
    public String staffLogin(@PathVariable String email, @PathVariable String password) {

        StaffRegistrationModel result = staffLoginFeignClient.staffLogin(email, password);
        if (Objects.nonNull(result)) {
            return "Login";
        }

        return "Failed";
    }

    // used by the staff
    @GetMapping("/getPatientAppointment")
    public ResponseEntity<List<PatientModel>> getPatientAppointment() {

        log.info("Sending to the service layer");
        List<PatientModel> staff = staffService.getPatientAppointment();
        if (Objects.nonNull(staff)) {

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(staff);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);

    }

    // used by the staff
    @PutMapping("/cancelingTheAppointmnet/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<String> cancelingTheAppointmnet(@PathVariable String patientId, @PathVariable String problem,
            @PathVariable LocalDate appointmentDate) {

        Staff result = staffService.cancelingTheAppointmnet(patientId, problem, appointmentDate);

        if (result!= null) {

            log.info("Sending to the patinet Module");
            patientFeignClient.cancelAppointment(patientId, problem, appointmentDate);

            log.info("FInding wheter the doctor is asigend or not ");
            if(result.getDoctorId()!= null){
            log.info("Sending to the Doctor Module");
            doctorFeignClient.cancelingTheAppointmnetByTheStaff(patientId,problem,appointmentDate);
            }
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("CANCELLED");
        }
        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(null);

    }

    // method used by the staff
    @GetMapping("/getAllCancelAppointent")
    public ResponseEntity<List<PatientModel>> getAllCancelAppointent() {

        List<PatientModel> staffs = staffService.getAllCancelAppointent();
        if (Objects.nonNull(staffs)) {

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(staffs);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);

    }

    // method used by the staff
    @GetMapping("/getAllDoctorDetails")
    public List<DoctorRegistration> getAllDoctorDetails() {
        return doctorDetailsFromReg.getDoctorDetails();
    }

    // method used by the staff
    //SENDS doctorId,startingTime,endingTime,doctorName
    @PutMapping("/assignAndUpdatingDoctorToThePatient/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<DoctorAssign> assignDoctorToThePatient(@RequestBody DoctorAssign assign,
            @PathVariable String patientId, @PathVariable String problem, @PathVariable LocalDate appointmentDate) {

        log.info("sending to the service layer");
        DoctorAssign patient = staffService.assignAndUpdatingDoctorToThePatient(assign, patientId, problem,
                appointmentDate);

        if (Objects.nonNull(patient)) {

            log.info("Sending the asign details to patient", patient);

            patientFeignClient.assignDoctorToThePatient(assign, patientId, problem, appointmentDate);

            log.info("Sending the asign details to doctor", patient);
            doctorFeignClient.addingTheAssignDetailsToTheDoctorTable(patient);

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patient);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);

    }

    // used by the staff
    @GetMapping("/getTreatmentCompletedPatient")
    public ResponseEntity<List<CompletedPatientModel>> getTreatmentCompletedPatient() {

        log.info("Sending to the Service Layer");

        List<CompletedPatientModel> patients = staffService.getTreatmentCompletedPatient();

        if (Objects.nonNull(patients)) {

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patients);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);

    }

    // used by the staff
    //sends totalAmount
    @PutMapping("/placingTotalAmountOfPatient/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<BillingModel> placingTotalAmountOfPatient(@RequestBody BillingModel billingModel,
            @PathVariable String patientId,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate) {

        log.info("Sending to the Service Layer");

        BillingModel patients = staffService.placingTotalAmountOfPatient(billingModel, patientId, problem,
                appointmentDate);
        if (Objects.nonNull(patients)) {

            log.info("sending it to patient modules");
            patientFeignClient.placingTotalAmountOfPatient(billingModel, patientId, problem, appointmentDate);
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patients);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);
    }

    // used by the staff
    //sends insuranceCoverageAmount,claimStatus-PENDING,APPROVED,REJECTED
    @PutMapping("/checkingTheRemaingAumontAfterInsurance/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<String> checkingTheRemaingAumontAfterInsurance(@RequestBody BillingModel billingModel,
            @PathVariable String patientId,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate) {

        log.info("Sending it to the staff service layer");
        staffService.checkingTheRemaingAumontAfterInsurance(billingModel, patientId, problem, appointmentDate);

        log.info("sends to the patient service");
        patientFeignClient.checkingTheRemaingAumontAfterInsurance(billingModel, patientId, problem, appointmentDate);

        return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body("Updated The Amount Deatails");

    }

    // used it later
    @GetMapping("/billingPatientDetails/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<BillingModel> billingPatientDetails(@RequestBody BillingModel billingModel,
            @PathVariable String patientId,
            @PathVariable String problem, @PathVariable LocalDate appointmentDate) {

        log.info("Sending to the Service Layer");

        BillingModel patients = staffService.billingPatientDetails(billingModel, patientId, problem, appointmentDate);
        if (Objects.nonNull(patients)) {

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patients);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_GATEWAY).body(null);
    }

}
