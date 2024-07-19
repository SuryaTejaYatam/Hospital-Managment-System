package com.HealthCare.HealthCareRegistration.Service.Staff;

import com.HealthCare.HealthCareRegistration.Entity.StaffRegistration;

public interface StaffRegistrationService {

    StaffRegistration staffRegistration(StaffRegistration staffRegistration);

    StaffRegistration staffLogin(String email, String password);
    
}
