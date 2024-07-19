package com.HealthCare.HealthCarePatientService.Service.OtherServices.Doctor;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCarePatientService.Entity.Patient;
import com.HealthCare.HealthCarePatientService.Exception.PatientServiceCustomException;
import com.HealthCare.HealthCarePatientService.Model.MedicalRecordModel;
import com.HealthCare.HealthCarePatientService.Model.TeleconsultationModel;
import com.HealthCare.HealthCarePatientService.Repository.PatientRepsitory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientServiceForTheDoctorImp implements PatientServiceForTheDoctor{
    

    @Autowired
    private PatientRepsitory patientRepsitory;

    @Override
    public void assignTheUrl(TeleconsultationModel teleconsultationModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);
        log.info("if Condition");
        if (Objects.nonNull(patient)) {
            log.info("seting the url");
            patient.setConsultationUrl(teleconsultationModel.getConsultationUrl());
            patient.setTreatmentstatus("Pending");
            log.info("saving the url");
            patientRepsitory.save(patient);
        } else {
            throw new PatientServiceCustomException("THERE IS NO PATINET IS PRSENT AT THE PATINET SIDE ", "NO_PATINET");
        }

    }

    @Override
    public void treatmentStatus(TeleconsultationModel teleconsultationModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);
        log.info("if Condition");
        if (Objects.nonNull(patient)) {
            log.info("Treatement status");
            patient.setTreatmentstatus(teleconsultationModel.getTreatmentstatus());
            log.info("saving the Treatement status");
            patientRepsitory.save(patient);
        }
    }

    @Override
    public void MedicalRecord(MedicalRecordModel medicalRecordModel, String patientId, String problem,
            LocalDate appointmentDate) {

        log.info("Finding the patient");
        Patient patient = patientRepsitory.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);

        log.info("if Condition");
        if (Objects.nonNull(patient)) {

            patient.setDiagnosis(medicalRecordModel.getDiagnosis());
            patient.setMedicalHistory(medicalRecordModel.getMedicalHistory());
            patient.setMedications(medicalRecordModel.getMedications());
            patient.setTreatmentPlans(medicalRecordModel.getTreatmentPlans());
            log.info("Saving the medical Records");
            patientRepsitory.save(patient);
        }

    }

}
