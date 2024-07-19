package com.HealthCare.HealthCareDoctor.Service;

import java.time.LocalDate;
import java.util.List;

import com.HealthCare.HealthCareDoctor.Entity.Doctor;
import com.HealthCare.HealthCareDoctor.Model.MedicalRecordMode;
import com.HealthCare.HealthCareDoctor.Model.Patient;
import com.HealthCare.HealthCareDoctor.Model.TeleconsultationModel;

public interface DoctorSrevice {

    // void assignAndUpdatingDoctorToThePatient(PatientModel patientModel);

    List<Patient> getAllPatients(String doctorId);

    String sendingTheUrl(TeleconsultationModel teleconsultationModel,String patinetId, String problem, LocalDate appointmentDate);

    String treatmentStatus(TeleconsultationModel teleconsultationModel, String patinetId,String problem, LocalDate appointmentDate);

    List<Patient> sortPatientByTodayDate(String doctorId);

    Doctor medicalRecords(MedicalRecordMode medicalRecordModel, String patientId, String problem, LocalDate appointmentDate);
    
}
