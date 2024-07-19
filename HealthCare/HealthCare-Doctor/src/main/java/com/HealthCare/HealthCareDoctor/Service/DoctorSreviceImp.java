package com.HealthCare.HealthCareDoctor.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCareDoctor.Entity.Doctor;
import com.HealthCare.HealthCareDoctor.Exception.DoctorServiceCustomException;
import com.HealthCare.HealthCareDoctor.Feign.PatientTeleconsultationFeignClient;
import com.HealthCare.HealthCareDoctor.Model.MedicalRecordMode;
import com.HealthCare.HealthCareDoctor.Model.Patient;

import com.HealthCare.HealthCareDoctor.Model.TeleconsultationModel;
import com.HealthCare.HealthCareDoctor.Respository.DoctorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorSreviceImp implements DoctorSrevice {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientTeleconsultationFeignClient patientTeleconsultationFeignClient;

    @Override
    public List<Patient> getAllPatients(String doctorId) {

        log.info("Returning all the patient details according to the doctor id");
        List<Doctor> doctors = doctorRepository.findByDoctorId(doctorId);
        List<Patient> patients = new ArrayList<>();
        if (Objects.nonNull(doctors)) {
            for (Doctor doctor : doctors) {
                Patient patient = Patient.builder()
                        .age(doctor.getAge())
                        .appointmentDate(doctor.getAppointmentDate())
                        .appointmentStatus(doctor.getAppointmentStatus())
                        .bloodGroup(doctor.getBloodGroup())
                        .doctorName(doctor.getDoctorName())
                        .endingTime(doctor.getEndingTime())
                        .firstName(doctor.getFirstName())
                        .gender(doctor.getGender())
                        .lastName(doctor.getLastName())
                        .patientId(doctor.getPatientId())
                        .problem(doctor.getProblem())
                        .startingTime(doctor.getStartingTime())
                        .phoneNumber(doctor.getPhoneNumber())
                        .treatmentstatus(doctor.getTreatmentstatus())
                        .build();
                patients.add(patient);
            }
            return patients;
        }
        throw new DoctorServiceCustomException("NO PATIENTS ARE ASSIGNED TO YOU", "NO_PATIENTS");

    }

    @Override
    public String sendingTheUrl(TeleconsultationModel teleconsultationModel, String patinetId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient based on patient id");
        Doctor patient = doctorRepository.findByPatientIdAndProblemAndAppointmentDate(patinetId, problem,
                appointmentDate);

        log.info("Checking where the patient is null or not");
        if (Objects.nonNull(patient)) {

            log.info("Attaching the url");
            patient.setConsultationUrl(teleconsultationModel.getConsultationUrl());
            patient.setTreatmentstatus("Pending");
            log.info("Saveing the details");
            doctorRepository.save(patient);

            log.info("Sending the url to the patient service");
            patientTeleconsultationFeignClient.assignTheUrl(teleconsultationModel, patinetId, problem, appointmentDate);
            return "URL is attached";
        }
        throw new DoctorServiceCustomException("NO PATIENTS IS PRESENT WITH THE PATIENT ID", "INCORRECT ID");
    }



    @Override
    public List<Patient> sortPatientByTodayDate(String doctorId) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        log.info("Today's Date: {}", formattedDate);

        List<Doctor> doctors = doctorRepository.findByAppointmentDateAndDoctorId(currentDate, doctorId);
        List<Patient> patients = new ArrayList<>();
        if (Objects.nonNull(doctors)) {
            for (Doctor doctor : doctors) {
                Patient patient = Patient.builder()
                        .age(doctor.getAge())
                        .appointmentDate(doctor.getAppointmentDate())
                        .appointmentStatus(doctor.getAppointmentStatus())
                        .bloodGroup(doctor.getBloodGroup())
                        .doctorName(doctor.getDoctorName())
                        .endingTime(doctor.getEndingTime())
                        .firstName(doctor.getFirstName())
                        .gender(doctor.getGender())
                        .lastName(doctor.getLastName())
                        .patientId(doctor.getPatientId())
                        .problem(doctor.getProblem())
                        .startingTime(doctor.getStartingTime())
                        .phoneNumber(doctor.getPhoneNumber())
                        .treatmentstatus(doctor.getTreatmentstatus())
                        .build();
                patients.add(patient);
            }
            return patients;
        } else {
            throw new DoctorServiceCustomException("No patients are present with the date " + formattedDate,
                    "NO_PATIENTS");
        }
    }

    @Override
    public Doctor medicalRecords(MedicalRecordMode medicalRecordModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient based on patient id");
        Doctor patient = doctorRepository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("Checking where the patient is null or not");
        if (Objects.nonNull(patient)) {
            patient.setDiagnosis(medicalRecordModel.getDiagnosis());
            patient.setMedicalHistory(medicalRecordModel.getMedicalHistory());
            patient.setMedications(medicalRecordModel.getMedications());
            patient.setTreatmentPlans(medicalRecordModel.getTreatmentPlans());
            log.info("Saving the medical Records");
            doctorRepository.save(patient);
            return patient;
        }
        throw new DoctorServiceCustomException("NO PATIENTS IS PRESENT WITH THE PATIENT ID", "INCORRECT ID");
    }

    @Override
    public String treatmentStatus(TeleconsultationModel teleconsultationModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient based on patient id");
        Doctor patient = doctorRepository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("Checking where the patient is null or not");
        if (Objects.nonNull(patient)) {

            log.info("Treatement Status");
            patient.setTreatmentstatus(teleconsultationModel.getTreatmentstatus());
            log.info("Saveing the details");
            doctorRepository.save(patient);

            log.info("Sending the Treatment status to the patient service");
            patientTeleconsultationFeignClient.treatmentStatus(teleconsultationModel, patientId, problem,
                    appointmentDate);
            return "Treatment status is updated";
        }
        throw new DoctorServiceCustomException("NO PATIENTS IS PRESENT WITH THE PATIENT ID", "INCORRECT ID");
    }

}
