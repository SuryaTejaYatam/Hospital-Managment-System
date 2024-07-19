package com.HealthCare.HealthCarePaymentService.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthCare.HealthCarePaymentService.Entity.Payment;
import com.HealthCare.HealthCarePaymentService.Model.PaymentModel;
import com.HealthCare.HealthCarePaymentService.Service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService PaymentService;

    //sends amountPaying,paymentMode,referenceNumber
    @PutMapping("/paymenting/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<Payment> paymenting(@RequestBody PaymentModel paymentModel,
            @PathVariable String patientId,
            @PathVariable String problem, 
            @PathVariable LocalDate appointmentDate) {

        log.info("Sending to the service layer");
        Payment patient = PaymentService.paymenting(paymentModel, patientId, problem, appointmentDate);
        if (Objects.nonNull(patient)) {

            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patient);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
    }

    @GetMapping("/listOfPayments/{patientId}/{problem}/{appointmentDate}")
    public ResponseEntity<Payment> listOfPayments(@PathVariable String patientId, @PathVariable String problem,
                                                        @PathVariable LocalDate appointmentDate) {
         Payment patients = PaymentService.listOfPayments(patientId,problem, appointmentDate);
        if (Objects.nonNull(patients)) {
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).body(patients);
        }
        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(null);
    }
}
