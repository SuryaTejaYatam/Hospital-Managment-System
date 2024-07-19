package com.HealthCare.HealthCareStaff.Service.OtherService.Patient;

import java.time.LocalDate;

import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.PatientModel;

public interface StaffServicesForPatients {

    void saveingApppointmentDeatils(PatientModel patient);

    void updatingTheDeatils(String patientId, PatientModel patientModel, String problem);

    String cancelingTheAppointmnet(String patientId, String problem, LocalDate appointmentDate);

    void placingInsuranceDetails(BillingModel billingModel, String patientId, String problem,
            LocalDate appointmentDate);
}
