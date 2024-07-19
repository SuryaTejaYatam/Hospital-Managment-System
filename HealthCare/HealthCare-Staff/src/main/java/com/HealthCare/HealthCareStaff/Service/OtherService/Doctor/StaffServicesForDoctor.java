package com.HealthCare.HealthCareStaff.Service.OtherService.Doctor;

import java.time.LocalDate;

public interface StaffServicesForDoctor {

    void doctorTreatmentCompletedStatus(String patientId, String problem, LocalDate appointmentDate);

}
