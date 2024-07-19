package com.HealthCare.HealthCarePatientService.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HealthCare.HealthCarePatientService.Entity.Patient;

@Repository
public interface PatientRepsitory extends JpaRepository<Patient,Long>{


    Patient findByPatientIdAndProblem(String patientId, String problem);

    Patient findByPatientId(String patientId);

    Patient findByProblem(String problem);

    Patient findByAppointmentDate(LocalDate appointmentDate);

    Patient findByPatientIdAndProblemAndAppointmentDate(String patientId, String problem, LocalDate appointmentDate);
    
}
