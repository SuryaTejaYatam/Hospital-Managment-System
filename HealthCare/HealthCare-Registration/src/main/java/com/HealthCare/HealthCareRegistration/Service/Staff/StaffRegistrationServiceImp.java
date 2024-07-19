package com.HealthCare.HealthCareRegistration.Service.Staff;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCareRegistration.Entity.StaffRegistration;
import com.HealthCare.HealthCareRegistration.Exception.RegistrationServiceCustomException;
import com.HealthCare.HealthCareRegistration.Repository.StaffRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffRegistrationServiceImp implements StaffRegistrationService {

    @Autowired
    private StaffRegistrationRepository staffRegistrationRepository;

    @Override
    public StaffRegistration staffRegistration(StaffRegistration staffRegistration) {

        log.info("Adding the random id to the doctor");
        staffRegistration.setStaffId(UUID.randomUUID().toString());

        String pass = staffRegistration.getPassword();
        String repass = staffRegistration.getReTypePassword();

        log.info("Password: {}, ReTypePassword: {}", pass, repass);
        if (pass.equals(repass)) {

            log.info("THe Password and the REtypePassword is correct so saving the details ");
            staffRegistrationRepository.save(staffRegistration);

            return staffRegistration;
        } else {
            log.info("ReTypePassword is incorrect");
            throw new RegistrationServiceCustomException("REGISTRATION IS NOT COMPLETED",
                    "PASSWORD ENTERED DOES NOT MATCH");
        }

    }

    @Override
    public StaffRegistration staffLogin(String email, String password) {

        StaffRegistration staffRegistration = staffRegistrationRepository.findByEmailAndPassword(email, password);
        if (Objects.nonNull(staffRegistration)) {
            return staffRegistration;
        }
        throw new RegistrationServiceCustomException("NO STAFF PRESENT WITH THE CREDITINALS",
        "INCCORRECT CREDITALS");
    }
}
