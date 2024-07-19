package com.HealthCare.HealthCareStaff.Service.OtherService.Doctor;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCareStaff.Entity.Staff;
import com.HealthCare.HealthCareStaff.Repository.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StaffServicesForDoctorImp implements StaffServicesForDoctor {

    @Autowired
    private StaffRepository repository;

    @Override
    public void doctorTreatmentCompletedStatus(String patientId, String problem, LocalDate appointmentDate) {

        log.info("Finding the patient");
        Staff staff = repository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem, appointmentDate);

        if (Objects.nonNull(staff)) {
            log.info("Changing the treatement status to completed");
            staff.setTreatmentstatus("Completed");
            repository.save(staff);
        }

    }
}
