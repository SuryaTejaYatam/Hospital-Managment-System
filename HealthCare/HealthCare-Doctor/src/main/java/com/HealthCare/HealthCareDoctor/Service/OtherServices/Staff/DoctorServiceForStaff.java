package com.HealthCare.HealthCareDoctor.Service.OtherServices.Staff;

import java.time.LocalDate;

import com.HealthCare.HealthCareDoctor.Model.PatientModel;

public interface DoctorServiceForStaff {

    void assignAndUpdatingDoctorToThePatient(PatientModel patientModel);

    void cancelingTheAppointmnetByTheStaffOrByThePatinet(String patientId, String problem, LocalDate appointmentDate);
    
}
