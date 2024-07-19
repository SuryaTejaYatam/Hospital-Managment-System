package com.HealthCare.HealthCareRegistration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HealthCare.HealthCareRegistration.Entity.PatientRegistration;

@Repository
public interface PatientRegistrationRepository extends JpaRepository<PatientRegistration,Long>{

    PatientRegistration findByEmailAndPassword(String email, String password);

    PatientRegistration findByEmail(String email);
    
}
