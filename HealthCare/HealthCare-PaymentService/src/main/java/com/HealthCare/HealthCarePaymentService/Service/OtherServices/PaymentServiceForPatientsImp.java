package com.HealthCare.HealthCarePaymentService.Service.OtherServices;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;
import com.HealthCare.HealthCarePaymentService.Repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceForPatientsImp implements PaymentServiceForPatients {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void placingThePatinetDataTothePaymentTable(String patientId, Payment payment) {

        log.info("Finding the patient");
        Payment existiPayment = paymentRepository.findByPatientIdAndProblemAndAppointmentDate(patientId,
                payment.getProblem(),
                payment.getAppointmentDate());
        if (existiPayment == null) {
            log.info("Placing the user data in the Payment Table");

            Payment patient = new Payment();
            patient.setPatientId(patientId);
            patient.setFirstName(payment.getFirstName());
            patient.setLastName(payment.getLastName());
            patient.setProblem(payment.getProblem());
            patient.setAppointmentDate(payment.getAppointmentDate());
            patient.setAmountDueAfterInsurance(payment.getAmountDueAfterInsurance());

            log.info("saving the patinet details");
            paymentRepository.save(patient);

        } else {
            existiPayment.setAmountDueAfterInsurance(payment.getAmountDueAfterInsurance());

            log.info("saving the patinet details");
            paymentRepository.save(existiPayment);
        }
    }

}
