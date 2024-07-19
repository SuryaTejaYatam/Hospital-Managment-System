package com.HealthCare.HealthCareRegistration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HealthCare.HealthCareRegistration.Entity.StaffRegistration;

@Repository
public interface StaffRegistrationRepository extends JpaRepository<StaffRegistration,Long>{

    StaffRegistration findByEmailAndPassword(String email, String password);
    
}
