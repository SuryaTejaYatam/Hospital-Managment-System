package com.HealthCare.HealthCarePaymentService.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;
import com.HealthCare.HealthCarePaymentService.Exception.PaymentServiceCustomException;
import com.HealthCare.HealthCarePaymentService.FeignClinet.PatinetFeignClient;
import com.HealthCare.HealthCarePaymentService.Model.PaymentModel;
import com.HealthCare.HealthCarePaymentService.Repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PatinetFeignClient patinetFeignClient;

    @Override
    public Payment paymenting(PaymentModel paymentModel, String patientId, String problem, LocalDate appointmentDate) {

        LocalDate currentDate = LocalDate.now();

        log.info("Finding the patient");
        Payment patient = paymentRepository.findByPatientIdAndProblemAndAppointmentDate(patientId, problem,
                appointmentDate);
        if (Objects.nonNull(patient)) {
            log.info("Placing the amount details");

            // If the patient has already paid some amount
            if (Objects.nonNull(patient.getRemaingAmount())) {

                BigDecimal remainingAmount = patient.getRemaingAmount();
                BigDecimal newPayment = paymentModel.getAmountPaying();
                BigDecimal amountAfterPayment = remainingAmount.subtract(newPayment);

                if (amountAfterPayment.compareTo(BigDecimal.ZERO) < 0) {

                    throw new PaymentServiceCustomException("The amount paying is greater than the reaming amount",
                            "The amount paying is greater than the reaming amount");
                }

                patient.setRemaingAmount(amountAfterPayment);

                // Update other payment details
                patient.setAmountPaying(paymentModel.getAmountPaying());
                patient.setPaymentMode(paymentModel.getPaymentMode());
                patient.setReferenceNumber(paymentModel.getReferenceNumber());
                patient.setPaymentDate(currentDate);

                log.info("Saving the details");
                paymentRepository.save(patient);

                if (amountAfterPayment.compareTo(BigDecimal.ZERO) == 0) {
                    log.info("sending the completed status to the patient service after paying the reaming due");
                    patinetFeignClient.amountFullyPaid(patientId, problem, appointmentDate);
                }
            } else {
                // If the patient has not paid any amount previously
                BigDecimal amountAfterPayment = patient.getAmountDueAfterInsurance()
                        .subtract(paymentModel.getAmountPaying());

                if (amountAfterPayment.compareTo(BigDecimal.ZERO) < 0) {

                    throw new PaymentServiceCustomException("The amount paying is greater than the reaming amount",
                            "The amount paying is greater than the reaming amount");
                }

                patient.setRemaingAmount(amountAfterPayment);

                // Update other payment details
                patient.setAmountPaying(paymentModel.getAmountPaying());
                patient.setPaymentMode(paymentModel.getPaymentMode());
                patient.setReferenceNumber(paymentModel.getReferenceNumber());
                patient.setPaymentDate(currentDate);

                log.info("Saving the details");
                paymentRepository.save(patient);

                if (amountAfterPayment.compareTo(BigDecimal.ZERO) == 0) {
                    // Notify that the amount has been fully paid
                    log.info(
                            "sending the completed status to the patient service after paying the reaming due at a time");
                    patinetFeignClient.amountFullyPaid(patientId, problem, appointmentDate);
                }
            }

            return patient;
        }
        return null;
    }

    @Override
    public Payment listOfPayments(String problem, String patientId, LocalDate appointmentDate) {

        log.info("Finding the patient");
        Payment patients = paymentRepository.findByPatientIdAndProblemAndAppointmentDate( patientId,problem,
                appointmentDate);

        return patients;
    }
}