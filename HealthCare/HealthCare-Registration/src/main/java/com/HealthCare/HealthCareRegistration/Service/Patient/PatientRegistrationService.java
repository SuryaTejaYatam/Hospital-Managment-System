package com.HealthCare.HealthCareRegistration.Service.Patient;

import com.HealthCare.HealthCareRegistration.Entity.PatientRegistration;

public interface PatientRegistrationService {

    PatientRegistration patientRegistration(PatientRegistration patientRegistration);

    PatientRegistration PatientLogin(String email, String password);
    
}
