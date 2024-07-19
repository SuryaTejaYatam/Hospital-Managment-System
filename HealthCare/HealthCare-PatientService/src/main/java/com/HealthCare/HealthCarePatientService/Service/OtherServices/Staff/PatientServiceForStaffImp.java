package com.HealthCare.HealthCarePatientService.Service.OtherServices.Staff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCarePatientService.Entity.Patient;
import com.HealthCare.HealthCarePatientService.Exception.PatientServiceCustomException;
import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.DoctorAssign;
import com.HealthCare.HealthCarePatientService.Repository.PatientRepsitory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientServiceForStaffImp implements PatientServiceForStaff {

    @Autowired
    private PatientRepsitory patientRepsitory;

    @Override
    public String cancelAppointment(String patientId, String problem, LocalDate appointmentDate) {

        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);
        if (Objects.nonNull(patient)) {
            log.info("Setting the updated date");
            patient.setAppointmentStatus("Cancelled");
            patient.setDoctorName(null);
            patient.setEndingTime(null);
            patient.setStartingTime(null);
            patient.setTreatmentstatus(null);
            patientRepsitory.save(patient);
            return "Cancelled";
        }
        throw new PatientServiceCustomException("THERE IS NO APPOINTMENT IS CREATED TO CANCEL",
                "APPOINTMENT_IS_NOT_CREATED");
    }

    @Override
    public void assignDoctorToThePatient(DoctorAssign doctorAssign, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);
        if (Objects.nonNull(patient)) {
            log.info("Assiging the doctor to the patient");
            patient.setDoctorName(doctorAssign.getDoctorName());
            patient.setEndingTime(doctorAssign.getEndingTime());
            patient.setStartingTime(doctorAssign.getStartingTime());
            patient.setAppointmentStatus("Doctor Assigned");
            patient.setTreatmentstatus("Pending");
            patientRepsitory.save(patient);

        }

    }

    @Override
    public void placingTotalAmountOfPatient(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // String formattedDate = currentDate.format(formatter);

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("if Condition");
        if (Objects.nonNull(patient)) {

            patient.setBillingDate(currentDate);
            patient.setTotalAmount(billingModel.getTotalAmount());
            patient.setInsuranceProvider("DETAILS SHOULD PROVIDE BY THE PATINET");
            patient.setInsurancePolicyNumber("DETAILS SHOULD PROVIDE BY THE PATINET");

            log.info("Saving the Total Amount details");
            patientRepsitory.save(patient);
        } else {
            throw new PatientServiceCustomException("THERE IS NO PATINET  ", "NO_PATINET");
        }

    }

    @Override
    public void checkingTheRemaingAumontAfterInsurance(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info(
                "Details are InsuranceCoverageAmount: {}, AmountDueAfterInsurance: {}, ClaimStatus: {}, PaymentStatus: {}",
                billingModel.getInsuranceCoverageAmount(),
                billingModel.getAmountDueAfterInsurance(),
                billingModel.getClaimStatus(),
                "Pending" // assuming payment status is always "Pending" based on your code
        );

        log.info("checking whether the patients is empty or not");
        if (Objects.nonNull(patient)) {

            log.info("Checking the patint ios Approved or not");
            if (billingModel.getClaimStatus().name().equals("APPROVED")) {

                log.info("Details are {}", billingModel.getAmountDueAfterInsurance());
                patient.setInsuranceCoverageAmount(billingModel.getInsuranceCoverageAmount());

                BigDecimal amount = patient.getTotalAmount().subtract(billingModel.getInsuranceCoverageAmount());

                patient.setAmountDueAfterInsurance(amount);
                patient.setClaimStatus(billingModel.getClaimStatus());
                patient.setPaymentStatus("Pending");

                log.info("Saving details");
                patientRepsitory.save(patient);
            } else if (billingModel.getClaimStatus().name().equals("PENDING")) {

                log.info("Checking whether the patient's claim status is pending");
                patient.setClaimStatus(billingModel.getClaimStatus());
                patient.setPaymentStatus("Pending");

                log.info("Saving the details");
                patientRepsitory.save(patient);

            } else if (billingModel.getClaimStatus().name().equals("REJECTED")) {

                log.info("The claim status is rejected");
                patient.setClaimStatus(billingModel.getClaimStatus());
                patient.setAmountDueAfterInsurance(patient.getTotalAmount());
                patient.setInsuranceCoverageAmount(BigDecimal.ZERO);
                patient.setPaymentStatus("Pending");

                log.info("Saving the details");
                patientRepsitory.save(patient);

            }
        } else {
            throw new PatientServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS",
                    "INCORECCT_DETAILS");

        }

    }

}
