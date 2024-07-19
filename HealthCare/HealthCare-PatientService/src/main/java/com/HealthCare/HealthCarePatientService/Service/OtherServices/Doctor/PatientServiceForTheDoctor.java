package com.HealthCare.HealthCarePatientService.Service.OtherServices.Doctor;

import java.time.LocalDate;

import com.HealthCare.HealthCarePatientService.Model.MedicalRecordModel;
import com.HealthCare.HealthCarePatientService.Model.TeleconsultationModel;

public interface PatientServiceForTheDoctor {

    void assignTheUrl(TeleconsultationModel teleconsultationModel, String patientId, String problem,
            LocalDate appointmentDate);

    void treatmentStatus(TeleconsultationModel teleconsultationModel, String patientId, String problem,
            LocalDate appointmentDate);

    void MedicalRecord(MedicalRecordModel medicalRecordModel, String patientId, String problem,
            LocalDate appointmentDate);
}
