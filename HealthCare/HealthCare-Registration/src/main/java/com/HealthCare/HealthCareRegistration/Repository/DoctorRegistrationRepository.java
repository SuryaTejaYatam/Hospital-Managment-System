package com.HealthCare.HealthCareRegistration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HealthCare.HealthCareRegistration.Entity.DoctorRegistration;

@Repository
public interface DoctorRegistrationRepository extends JpaRepository<DoctorRegistration,Long> {

    DoctorRegistration findByEmailAndPassword(String email, String password);
    
}
