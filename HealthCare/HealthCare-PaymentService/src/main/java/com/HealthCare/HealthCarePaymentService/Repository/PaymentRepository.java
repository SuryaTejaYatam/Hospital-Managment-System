package com.HealthCare.HealthCarePaymentService.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{

    Payment findByPatientIdAndProblemAndAppointmentDate(String patientId, String problem, LocalDate appointmentDate);

    // List<Payment> findByProblemAndPatientIdAndAppointmentDate(String problem, String patientId,
    //         LocalDate appointmentDate);
    
}
