package com.HealthCare.HealthCareStaff.Service;

import java.time.LocalDate;
import java.util.List;

import com.HealthCare.HealthCareStaff.Entity.Staff;
import com.HealthCare.HealthCareStaff.Model.BillingModel;
import com.HealthCare.HealthCareStaff.Model.CompletedPatientModel;
import com.HealthCare.HealthCareStaff.Model.DoctorAssign;
import com.HealthCare.HealthCareStaff.Model.PatientModel;

public interface StaffService {

        List<PatientModel> getPatientAppointment();

        // void saveingApppointmentDeatils(PatientModel patient);

        // void updatingTheDeatils(String patientId, PatientModel patientModel, String
        // problem);

        // void cancelAppointmentFromThePatient(String patientId, String problem);

        List<PatientModel> getAllCancelAppointent();

        DoctorAssign assignAndUpdatingDoctorToThePatient(DoctorAssign assign, String patientId, String problem,
                        LocalDate appointmentDate);

        Staff cancelingTheAppointmnet(String patientId, String problem, LocalDate
        appointmentDate);

        // void doctorTreatmentCompletedStatus(String patientId, String problem, LocalDate appointmentDate);

        List<CompletedPatientModel> getTreatmentCompletedPatient();

        BillingModel billingPatientDetails(BillingModel billingModel, String patientId, String problem,
                        LocalDate appointmentDate);

        BillingModel placingTotalAmountOfPatient(BillingModel billingModel, String patientId, String problem,
                        LocalDate appointmentDate);

        // void placingInsuranceDetails(BillingModel billingModel, String patientId,
        // String problem,
        // LocalDate appointmentDate);

        String checkingTheRemaingAumontAfterInsurance(BillingModel billingModel, String patientId, String problem,
                        LocalDate appointmentDate);

}
