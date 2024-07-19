package com.HealthCare.HealthCarePatientService.Service;

import java.time.LocalDate;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCarePatientService.Entity.Patient;
import com.HealthCare.HealthCarePatientService.Exception.PatientServiceCustomException;
import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.DoctorAssign;
import com.HealthCare.HealthCarePatientService.Model.MedicalRecordModel;
import com.HealthCare.HealthCarePatientService.Model.PatientModel;
import com.HealthCare.HealthCarePatientService.Model.PatientResgistration;
import com.HealthCare.HealthCarePatientService.Repository.PatientRepsitory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientServiceImp implements PatientService {

    @Autowired
    private PatientRepsitory patientRepsitory;

    public Patient createAppointment(PatientModel patientModel, PatientResgistration patientRegistration) {
        LocalDate currentDate = LocalDate.now();

        log.info("Find the patinet is prsent with the same deatils ");
        Patient existingPatient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(
                patientRegistration.getPatientId(), patientModel.getProblem(), patientModel.getAppointmentDate());

        if (!currentDate.isAfter(patientModel.getAppointmentDate())) {
            if (existingPatient == null) {
                // Create a new patient if no existing match is found
                log.info("Creating new patient");
                Patient newPatient = Patient.builder()
                        .age(patientRegistration.getAge())
                        .appointmentDate(patientModel.getAppointmentDate())
                        .bloodGroup(patientRegistration.getBloodGroup())
                        .firstName(patientRegistration.getFirstName())
                        .gender(patientRegistration.getGender())
                        .lastName(patientRegistration.getLastName())
                        .phoneNumber(patientRegistration.getPhoneNumber())
                        .problem(patientModel.getProblem())
                        .todayDate(currentDate)
                        .appointmentStatus("Will assign to the Doctor")
                        .patientId(patientRegistration.getPatientId())
                        .build();

                log.info("Saving new patient details");
                patientRepsitory.save(newPatient);
                return newPatient;
            } else {

                if ("Cancelled".equals(existingPatient.getAppointmentStatus())) {
                    existingPatient.setAppointmentStatus("Will assign to the Doctor");
                    log.info("Updating appointment status for existing patient");
                    patientRepsitory.save(existingPatient);
                } else {
                    log.info("Appointment already exists and is not cancelled.");
                }
                return existingPatient;
            }
        }
        throw new PatientServiceCustomException("UPDATE THE DATE FOR THE APPOINMENT", "UPDATE_DATE");
    }

    @Override
    public PatientModel appointmentDetails(String patientId, String problem) {

        log.info("Sending the Details");
        Patient patient = patientRepsitory.findByPatientIdAndProblem(patientId, problem);
        if (Objects.nonNull(patient)) {
            PatientModel patientModel = PatientModel.builder()
                    .appointmentDate(patient.getAppointmentDate())
                    .doctorName(patient.getDoctorName())
                    .endingTime(patient.getEndingTime())
                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    // .patientId(patientId)
                    .problem(problem)
                    .startingTime(patient.getStartingTime()).build();
            return patientModel;
        }

        throw new PatientServiceCustomException("THERE IS NO APPOINTMENT IS CREATED ", "APPOINTMENT_IS_NOT_CREATED");
    }

    @Override
    public Patient updateAppointment(String patientId, PatientModel patientModel, String problem) {

        log.info("Sastifys the condition,finding the deatils");
        Patient patient = patientRepsitory.findByPatientIdAndProblem(patientId, problem);
        if (Objects.nonNull(patient)) {
            log.info("Setting the updated date");
            patient.setAppointmentDate(patientModel.getAppointmentDate());
            patientRepsitory.save(patient);
            return patient;
        }
        throw new PatientServiceCustomException("THERE IS NO APPOINTMENT IS CREATED TO UPDATE ",
                "APPOINTMENT_IS_NOT_CREATED");
    }

   

    @Override
    public Patient patientCancelTheAppointment(String patientId, String problem, LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);
        log.info("If condition");
        if (Objects.nonNull(patient)) {
            log.info("Setting the updated date");
            patient.setAppointmentStatus("Cancelled");
            patient.setDoctorName(null);
            patient.setEndingTime(null);
            patient.setStartingTime(null);
            patient.setTreatmentstatus(null);
            patientRepsitory.save(patient);
            return patient;
        }
        throw new PatientServiceCustomException("THERE IS NO APPOINTMENT IS CREATED TO CANCEL",
                "APPOINTMENT_IS_NOT_CREATED");
    }

   

    @Override
    public DoctorAssign checkingTheAssigingDoctor(String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("if Condition");
        if (Objects.nonNull(patient)) {

            DoctorAssign newPatient = DoctorAssign.builder()
                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    .problem(problem)
                    .appointmentDate(appointmentDate)
                    .doctorName(patient.getDoctorName())
                    .endingTime(patient.getEndingTime())
                    .startingTime(patient.getStartingTime())
                    .consultationUrl(patient.getConsultationUrl())
                    .treatmentstatus(patient.getTreatmentstatus()).build();

            return newPatient;

        } else {
            throw new PatientServiceCustomException("THERE IS NO PATINET  ", "NO_PATINET");
        }
    }

    @Override
    public MedicalRecordModel gettingTheMedicalRecordofpatinet(String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("if Condition");
        if (Objects.nonNull(patient)) {

            MedicalRecordModel newPatient = MedicalRecordModel.builder()
                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    .problem(problem)
                    .appointmentDate(appointmentDate)
                    .doctorName(patient.getDoctorName())
                    .diagnosis(patient.getDiagnosis())
                    .medications(patient.getMedications())
                    .treatmentPlans(patient.getTreatmentPlans())
                    .medicalHistory(patient.getMedicalHistory())
                    .build();

            return newPatient;
        } else {
            throw new PatientServiceCustomException("THERE IS NO PATINET  ", "NO_PATINET");
        }

    }

   

    @Override
    public BillingModel gettingThePatientBillingDetails(String patientId, String problem, LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        if (Objects.nonNull(patient)) {
            BillingModel billingModel2 = BillingModel.builder()

                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    .problem(patient.getProblem())
                    .appointmentDate(patient.getAppointmentDate())
                    .doctorName(patient.getDoctorName())
                    .totalAmount(patient.getTotalAmount())
                    .billingDate(patient.getBillingDate())
                    .insuranceProvider(patient.getInsuranceProvider())
                    .insurancePolicyNumber(patient.getInsurancePolicyNumber())
                    .insuranceCoverageAmount(patient.getInsuranceCoverageAmount())
                    .amountDueAfterInsurance(patient.getAmountDueAfterInsurance())
                    .paymentCompletedDate(patient.getPaymentCompletedDate())
                    .paymentStatus(patient.getPaymentStatus())
                    .claimStatus(patient.getClaimStatus()).build();

            return billingModel2;
        }
        throw new PatientServiceCustomException("THERE IS NO PATINET  ", "NO_PATINET");
    }

    @Override
    public Patient placingInsuranceDetails(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("if Condition");
        if (Objects.nonNull(patient)) {

            patient.setInsuranceProvider(billingModel.getInsuranceProvider());
            patient.setInsurancePolicyNumber(billingModel.getInsurancePolicyNumber());

            log.info("Saving the Total Amount details");
            patientRepsitory.save(patient);
            return patient;
        } else {
            throw new PatientServiceCustomException("THERE IS NO PATINET  ", "NO_PATINET");
        }
    }

    @Override
    public void amountFullyPaid(String patientId, String problem, LocalDate appointmentDate) {

        LocalDate currentDate = LocalDate.now();

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("if Condition");
        if (Objects.nonNull(patient)) {

            patient.setPaymentCompletedDate(currentDate);
            patient.setPaymentStatus("Completed");

            log.info("Saving the  Amount details");
            patientRepsitory.save(patient);

        }
    }

   

}
