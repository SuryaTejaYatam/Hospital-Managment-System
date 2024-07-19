package com.HealthCare.HealthCareRegistration.Service.Patient;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCareRegistration.Entity.PatientRegistration;
import com.HealthCare.HealthCareRegistration.Exception.RegistrationServiceCustomException;
import com.HealthCare.HealthCareRegistration.Repository.PatientRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientRegistrationServiceImp implements PatientRegistrationService {

    @Autowired
    private PatientRegistrationRepository patientRegistrationRepository;

    @Override
    public PatientRegistration patientRegistration(PatientRegistration patientRegistration) {

        try {
            String pass = patientRegistration.getPassword();
            String repass = patientRegistration.getReTypePassword();

            log.info("Password: {}, ReTypePassword: {}", pass, repass);

            if (pass.equals(repass)) {

                log.info("THe Password and the REtypePassword is correct so saving the details ");
                patientRegistration.setPatientId(UUID.randomUUID().toString());
                patientRegistrationRepository.save(patientRegistration);

                return patientRegistration;
            } else {
                log.info("ReTypePassword is incorrect");
                throw new RegistrationServiceCustomException("REGISTRATION IS NOT COMPLETED",
                        "PASSWORD ENTERED DOES NOT MATCH");
            }

        } catch (Exception e) {
            throw new RegistrationServiceCustomException("EMAIL ID IS ALREADY PRESENT",
                    "EMAIL_ID_PRESENT");
        }
    }

    @Override
    public PatientRegistration PatientLogin(String email, String password) {

        PatientRegistration patientRegistration = patientRegistrationRepository.findByEmailAndPassword(email, password);
        if (Objects.nonNull(patientRegistration)) {
            return patientRegistration;
        }

        throw new RegistrationServiceCustomException("NO PATIENT PRESENT WITH THE  CREDENTIALS",
                "INCORRECT CREDENTIALS");
    }
}
