package com.HealthCare.HealthCareStaff.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.HealthCare.HealthCareStaff.Entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long>{

    Staff findByFirstName(String firstName);

    Staff findByAppointmentDate(LocalDate appointmentDate);

    Staff findByProblem(String problem);

    Staff findByPatientIdAndProblem(String patientId, String problem);

    Staff findByPatientIdAndProblemAndAppointmentDate(String patientId, String problem, LocalDate appointmentDate);

    Staff findByPatientId(String patientId);
    
}
