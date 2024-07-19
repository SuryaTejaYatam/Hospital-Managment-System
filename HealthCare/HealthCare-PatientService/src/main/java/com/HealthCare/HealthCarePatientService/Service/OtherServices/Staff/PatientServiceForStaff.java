package com.HealthCare.HealthCarePatientService.Service.OtherServices.Staff;

import java.time.LocalDate;

import com.HealthCare.HealthCarePatientService.Model.BillingModel;
import com.HealthCare.HealthCarePatientService.Model.DoctorAssign;

public interface PatientServiceForStaff {
    
    String cancelAppointment(String patientId, String problem, LocalDate appointmentDate);

    void assignDoctorToThePatient(DoctorAssign doctorAssign, String patientId, String problem,
    LocalDate appointmentDate);

    void placingTotalAmountOfPatient(BillingModel billingModel, String patientId, String problem, LocalDate appointmentDate);

    void checkingTheRemaingAumontAfterInsurance(BillingModel billingModel, String patientId, String problem,
    LocalDate appointmentDate);
}
