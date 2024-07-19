package com.HealthCare.HealthCareStaff.Service.OtherService.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCareStaff.Entity.Staff;
import com.HealthCare.HealthCareStaff.Exception.StaffServiceCustomException;
import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.CompletedPatientModel;
import com.HealthCare.HealthCareStaff.Model.PatientModel;
import com.HealthCare.HealthCareStaff.Repository.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffServicesForPatientsImp implements StaffServicesForPatients {

    @Autowired
    private StaffRepository repository;

    @Override
    public void saveingApppointmentDeatils(PatientModel patient) {
        log.info("Finding the patient by Patient Id, Problem, and Appointment Date");
        Staff existingPatient = repository.findByPatientIdAndProblemAndAppointmentDate(
                patient.getPatientId(), patient.getProblem(), patient.getAppointmentDate());

        if (existingPatient == null) {
            // Create a new patient if no existing match is found
            log.info("Create new patient with the details");
            Staff patientDetails = Staff.builder()
                    .patientId(patient.getPatientId())
                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    .phoneNumber(patient.getPhoneNumber())
                    .gender(patient.getGender())
                    .age(patient.getAge())
                    .bloodGroup(patient.getBloodGroup())
                    .problem(patient.getProblem())
                    .appointmentDate(patient.getAppointmentDate())
                    .appointmentStatus(patient.getAppointmentStatus())
                    .build();

            log.info("Saving the Patient details in the staff table");
            repository.save(patientDetails);
        } else if ("Cancelled".equals(existingPatient.getAppointmentStatus())) {
            // Update appointment status to "Will assign to the Doctor" for cancelled
            // appointments
            existingPatient.setAppointmentStatus("Will assign to the Doctor");
            log.info("Saving the details");
            repository.save(existingPatient);
            log.info("Details are saved ", existingPatient);
        } else {
            // Log a message for existing non-cancelled appointments
            log.info("Appointment already exists and is not cancelled.");
        }

        // No need for an exception as successful or informative messages are logged in
        // all cases.
    }

    @Override
    public void updatingTheDeatils(String patientId, PatientModel patientModel, String problem) {

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblem(patientId, problem);
        if (Objects.nonNull(staff)) {
            log.info("Setting the updated date");
            staff.setAppointmentDate(patientModel.getAppointmentDate());
            repository.save(staff);

        } else {
            throw new StaffServiceCustomException("NO PATIENTS ARE PRESENT IN THE STAFF SIDE ", "DETAILS_NOT_SAVED");
        }

    }

    @Override
    public String cancelingTheAppointmnet(String patientId, String problem,
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
            return "Cancled";
        }
        throw new StaffServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS ", "INCORECCT_DETAILS");

    }

   

    @Override
    public void placingInsuranceDetails(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        if (Objects.nonNull(staff)) {

            staff.setInsuranceProvider(billingModel.getInsuranceProvider());
            staff.setInsurancePolicyNumber(billingModel.getInsurancePolicyNumber());

            repository.save(staff);
        } else {
            throw new StaffServiceCustomException("NO PATIENTS IS  PRESENT WITH THE DETAILS IN THE STAFF MODULE",
                    "INCORECCT_DETAILS");
        }

    }

}
