package com.HealthCare.HealthCareRegistration.Service.Doctor;

import java.util.List;

import com.HealthCare.HealthCareRegistration.Entity.DoctorRegistration;

public interface DoctorRegistrationService {

    DoctorRegistration registration(DoctorRegistration doctorRegistration);

    DoctorRegistration doctorLogin(String email, String password);

    List<DoctorRegistration> getAllDoctorDetails();

    List<DoctorRegistration> getDoctorDetails();
    
}
