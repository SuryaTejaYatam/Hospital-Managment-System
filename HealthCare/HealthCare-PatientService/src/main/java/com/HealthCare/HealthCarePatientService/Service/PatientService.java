package com.HealthCare.HealthCarePatientService.Service;

import java.time.LocalDate;

import com.HealthCare.HealthCarePatientService.Entity.Patient;
import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.DoctorAssign;
import com.HealthCare.HealthCarePatientService.Model.MedicalRecordModel;
import com.HealthCare.HealthCarePatientService.Model.PatientModel;
import com.HealthCare.HealthCarePatientService.Model.PatientResgistration;
import com.HealthCare.HealthCarePatientService.Model.TeleconsultationModel;

public interface PatientService {

    Patient createAppointment(PatientModel patientModel, PatientResgistration patientrResgistration);

    PatientModel appointmentDetails(String patientId, String problem);

    Patient updateAppointment(String patientId, PatientModel patientModel, String problem);

//     String cancelAppointment(String patientId, String problem, LocalDate appointmentDate);



Patient patientCancelTheAppointment(String patientId, String problem, LocalDate appointmentDate);

//     void assignTheUrl(TeleconsultationModel teleconsultationModel, String patientId, String problem,
//             LocalDate appointmentDate);

//     void treatmentStatus(TeleconsultationModel teleconsultationModel, String patientId, String problem,
//             LocalDate appointmentDate);

//     void MedicalRecord(MedicalRecordModel medicalRecordModel, String patientId, String problem,
//     LocalDate appointmentDate);

    DoctorAssign checkingTheAssigingDoctor(String patientId, String problem, LocalDate appointmentDate);

    MedicalRecordModel gettingTheMedicalRecordofpatinet(String patientId, String problem, LocalDate appointmentDate);

//     void placingTotalAmountOfPatient(BillingModel billingModel, String patientId, String problem, LocalDate appointmentDate);

    BillingModel gettingThePatientBillingDetails(String patientId, String problem, LocalDate appointmentDate);

    Patient placingInsuranceDetails(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate);

    void amountFullyPaid(String patientId, String problem, LocalDate appointmentDate);

//     void checkingTheRemaingAumontAfterInsurance(BillingModel billingModel, String patientId, String problem,
//             LocalDate appointmentDate);


}
