package com.HealthCare.HealthCareStaff.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCareStaff.Entity.Staff;
import com.HealthCare.HealthCareStaff.Exception.StaffServiceCustomException;
import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.CompletedPatientModel;
import com.HealthCare.HealthCareStaff.Model.DoctorAssign;
import com.HealthCare.HealthCareStaff.Model.PatientModel;
import com.HealthCare.HealthCareStaff.Repository.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffServiceImp implements StaffService {

    @Autowired
    private StaffRepository repository;

    @Override
    public List<PatientModel> getPatientAppointment() {
        List<Staff> staffs = repository.findAll();
        List<PatientModel> validAppointments = new ArrayList<>();

        for (Staff staff : staffs) {
            if (staff.getAppointmentStatus().equalsIgnoreCase("Will assign to the Doctor")) {

                PatientModel patientModel = PatientModel.builder()
                        .age(staff.getAge())
                        .patientId(staff.getPatientId())
                        .firstName(staff.getFirstName())
                        .lastName(staff.getLastName())
                        .phoneNumber(staff.getPhoneNumber())
                        .gender(staff.getGender())
                        .bloodGroup(staff.getBloodGroup())
                        .appointmentStatus(staff.getAppointmentStatus())
                        .appointmentDate(staff.getAppointmentDate())
                        .problem(staff.getProblem()).build();
                validAppointments.add(patientModel);
            }
        }

        return validAppointments;

    }

   

    @Override
    public List<PatientModel> getAllCancelAppointent() {
        List<Staff> staffs = repository.findAll();
        List<PatientModel> cancledAppointments = new ArrayList<>();

        for (Staff staff : staffs) {
            if (staff.getAppointmentStatus().equalsIgnoreCase("Cancelled")) {

                PatientModel patientModel = PatientModel.builder()
                        .age(staff.getAge())
                        .patientId(staff.getPatientId())
                        .firstName(staff.getFirstName())
                        .lastName(staff.getLastName())
                        .phoneNumber(staff.getPhoneNumber())
                        .gender(staff.getGender())
                        .bloodGroup(staff.getBloodGroup())
                        .appointmentStatus(staff.getAppointmentStatus())
                        .appointmentDate(staff.getAppointmentDate())
                        .problem(staff.getProblem()).build();
                cancledAppointments.add(patientModel);
            }
        }

        return cancledAppointments;
    }

    @Override
    public Staff cancelingTheAppointmnet(String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        if (Objects.nonNull(staff)) {
            log.info("Canceling the AppointMnet");
            staff.setDoctorName(null);
            staff.setEndingTime(null);
            staff.setStartingTime(null);
            staff.setAppointmentStatus("Cancelled");
            staff.setTreatmentstatus(null);
            repository.save(staff);
            return staff;
        }
        throw new StaffServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS ", "INCORECCT_DETAILS");

    }
    @Override
    public List<CompletedPatientModel> getTreatmentCompletedPatient() {

        log.info("Getting all the patients");
        List<Staff> patients = repository.findAll();

        List<CompletedPatientModel> treatmentCompletedPatient = new ArrayList<>();

        for (Staff patient : patients) {

            log.info("Finding the Treatment Completed patients");
            String treatmentStatus = patient.getTreatmentstatus();
            if ("Completed".equals(treatmentStatus)) {

                CompletedPatientModel completedPatientModel = CompletedPatientModel.builder()
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .problem(patient.getProblem())
                        .appointmentDate(patient.getAppointmentDate())
                        .startingTime(patient.getStartingTime())
                        .endingTime(patient.getEndingTime())
                        .doctorName(patient.getDoctorName())
                        .treatmentstatus(patient.getTreatmentstatus()).build();

                log.info("Adding the patients to the ArrayList");
                treatmentCompletedPatient.add(completedPatientModel);

            }

        }
        return treatmentCompletedPatient;

    }

    @Override
    public DoctorAssign assignAndUpdatingDoctorToThePatient(DoctorAssign assign, String patientId,
            String problem, LocalDate appointmentDate) {

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        if (Objects.nonNull(staff)) {
            log.info("Assigning the doctor to the patient");
            staff.setDoctorName(assign.getDoctorName());
            staff.setEndingTime(assign.getEndingTime());
            staff.setStartingTime(assign.getStartingTime());
            staff.setDoctorId(assign.getDoctorId());
            staff.setAppointmentStatus("Doctor Assigned");
            staff.setTreatmentstatus("Pending");
            repository.save(staff);

            // Create a new instance of DoctorAssign and set its values
            DoctorAssign doctorAssign = new DoctorAssign();
            doctorAssign.setPatientId(staff.getPatientId());
            doctorAssign.setFirstName(staff.getFirstName());
            doctorAssign.setLastName(staff.getLastName());
            doctorAssign.setPhoneNumber(staff.getPhoneNumber());
            doctorAssign.setGender(staff.getGender());
            doctorAssign.setAge(staff.getAge());
            doctorAssign.setBloodGroup(staff.getBloodGroup());
            doctorAssign.setAppointmentStatus(staff.getAppointmentStatus());
            doctorAssign.setProblem(staff.getProblem());
            doctorAssign.setAppointmentDate(staff.getAppointmentDate());
            doctorAssign.setDoctorId(staff.getDoctorId());
            doctorAssign.setStartingTime(staff.getStartingTime());
            doctorAssign.setEndingTime(staff.getEndingTime());
            doctorAssign.setDoctorName(staff.getDoctorName());
            doctorAssign.setTreatmentstatus(staff.getTreatmentstatus());

            return doctorAssign;
        }
        throw new StaffServiceCustomException("NO PATIENT IS PRESENT WITH THE DETAILS", "INCORRECT_DETAILS");
    }

   

    @Override
    public BillingModel billingPatientDetails(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        if (Objects.nonNull(staff)) {
            BillingModel billingModel2 = BillingModel.builder()

                    .firstName(staff.getFirstName())
                    .lastName(staff.getLastName())
                    .problem(staff.getProblem())
                    .appointmentDate(staff.getAppointmentDate())
                    .doctorName(staff.getDoctorName())
                    .totalAmount(staff.getTotalAmount())
                    .billingDate(staff.getBillingDate())
                    .insuranceProvider(staff.getInsuranceProvider())
                    .insurancePolicyNumber(staff.getInsurancePolicyNumber())
                    .insuranceCoverageAmount(staff.getInsuranceCoverageAmount())
                    .amountDueAfterInsurance(staff.getAmountDueAfterInsurance())
                    .paymentCompletedDate(staff.getPaymentCompletedDate())
                    .paymentStatus(staff.getPaymentStatus())
                    .claimStatus(staff.getClaimStatus()).build();

            return billingModel2;
        }
        throw new StaffServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS ", "INCORECCT_DETAILS");

    }

    @Override
    public BillingModel placingTotalAmountOfPatient(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // String formattedDate = currentDate.format(formatter);

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        if (Objects.nonNull(staff)) {

            staff.setTotalAmount(billingModel.getTotalAmount());
            staff.setBillingDate(currentDate);
            staff.setInsuranceProvider("DETAILS SHOULD PROVIDE BY THE PATINET");
            staff.setInsurancePolicyNumber("DETAILS SHOULD PROVIDE BY THE PATINET");
            log.info("Saving the billing details");
            repository.save(staff);

            BillingModel billingModel2 = BillingModel.builder()
                    .firstName(staff.getFirstName())
                    .lastName(staff.getLastName())
                    .problem(staff.getProblem())
                    .appointmentDate(staff.getAppointmentDate())
                    .doctorName(staff.getDoctorName())
                    .totalAmount(staff.getTotalAmount())
                    .billingDate(currentDate)
                    .insuranceProvider("DETAILS SHOULD PROVIDE BY THE PATINET")
                    .insurancePolicyNumber("DETAILS SHOULD PROVIDE BY THE PATINET").build();

            return billingModel2;

        }
        throw new StaffServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS ", "INCORECCT_DETAILS");
    }

    

    @Override
    public String checkingTheRemaingAumontAfterInsurance(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {
        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        log.info("Checking if the patient exists");
        if (Objects.nonNull(staff)) {

            log.info("Checking whether the patient's claim status is approved");
            if (billingModel.getClaimStatus().name().equals("APPROVED")) {

                staff.setInsuranceCoverageAmount(billingModel.getInsuranceCoverageAmount());
                BigDecimal amount = staff.getTotalAmount().subtract(billingModel.getInsuranceCoverageAmount());

                staff.setAmountDueAfterInsurance(amount);
                staff.setClaimStatus(billingModel.getClaimStatus());
                staff.setPaymentStatus("Pending");

                log.info("Saving the details");
                repository.save(staff);

                return "Amount is updated after insurance";

            } else if (billingModel.getClaimStatus().name().equals("PENDING")) {

                log.info("Checking whether the patient's claim status is pending");
                staff.setClaimStatus(billingModel.getClaimStatus());
                staff.setPaymentStatus("Pending");

                log.info("Saving the details");
                repository.save(staff);

                return "Claim status is pending";

            } else if (billingModel.getClaimStatus().name().equals("REJECTED")) {

                log.info("The claim status is rejected");
                staff.setClaimStatus(billingModel.getClaimStatus());
                staff.setAmountDueAfterInsurance(staff.getTotalAmount());
                staff.setInsuranceCoverageAmount(BigDecimal.ZERO);
                staff.setPaymentStatus("Pending");

                log.info("Saving the details");
                repository.save(staff);

                return "Claim status is rejected";
            }
        } else {
            throw new StaffServiceCustomException("No patient is present with the details in the staff module",
                    "INCORRECT_DETAILS");
        }
        return null;
    }

    // @Override
    // public void doctorTreatmentCompletedStatus(String patientId, String problem, LocalDate appointmentDate) {

    //     log.info("Finding the patient");
    //     Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

    //     if (Objects.nonNull(staff)) {
    //         log.info("Changing the treatement status to completed");
    //         staff.setTreatmentstatus("Completed");
    //         repository.save(staff);
    //     }
    //     throw new StaffServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS ", "INCORECCT_DETAILS");
    // }


}
