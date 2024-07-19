package com.HealthCare.HealthCareDoctor.Respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HealthCare.HealthCareDoctor.Entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long>{


    List<Doctor> findByDoctorId(String doctorId);

    Doctor findByPatientId(String patientId);

    List<Doctor> findByAppointmentDate(LocalDate currentDate);

    Doctor findByProblem(String problem);

    Doctor findByPatientIdAndProblemAndAppointmentDateAndDoctorId(String patientId, String problem,
            LocalDate appointmentDate, String doctorId);

    Doctor findByPatientIdAndProblemAndAppointmentDate(String patinetId, String problem, LocalDate appointmentDate);

    List<Doctor> findByAppointmentDateAndDoctorId(LocalDate currentDate, String doctorId);
    
}
