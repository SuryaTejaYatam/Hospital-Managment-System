package com.HealthCare.HealthCareRegistration.Service.Doctor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCareRegistration.Entity.DoctorRegistration;
import com.HealthCare.HealthCareRegistration.Exception.RegistrationServiceCustomException;
import com.HealthCare.HealthCareRegistration.Repository.DoctorRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorRegistrationServiceImp implements DoctorRegistrationService {

    @Autowired
    private DoctorRegistrationRepository doctorRegistrationRepository;

    @Override
    public DoctorRegistration registration(DoctorRegistration doctorRegistration) {

        String d_id = UUID.randomUUID().toString();

        log.info("Adding the random id to the doctor");
        doctorRegistration.setDoctorId(d_id);

        String pass = doctorRegistration.getPassword();
        String repass = doctorRegistration.getReTypePassword();

        log.info("Password: {}, ReTypePassword: {}", pass, repass); 

        if (pass.equals(repass)) {
            log.info("The Password and the ReTypePassword match, saving the details");
            doctorRegistrationRepository.save(doctorRegistration);
            return doctorRegistration;
        } else {
            log.info("ReTypePassword is incorrect");
            throw new RegistrationServiceCustomException("REGISTRATION IS NOT COMPLETED",
                    "PASSWORD ENTERED DOES NOT MATCH");
        }
    }

    @Override
    public DoctorRegistration doctorLogin(String email, String password) {

        DoctorRegistration doctorRegistration = doctorRegistrationRepository.findByEmailAndPassword(email, password);

        if (Objects.nonNull(doctorRegistration)) {
            return doctorRegistration;
        }
        throw new RegistrationServiceCustomException("NO DOCTOR PRESENT WITH THE CREDITINALS",
                "INCCORRECT CREDITALS");
    }

    @Override
    public List<DoctorRegistration> getAllDoctorDetails() {

        return doctorRegistrationRepository.findAll();

    }

    @Override
    public List<DoctorRegistration> getDoctorDetails() {

        return doctorRegistrationRepository.findAll();
    }
}
