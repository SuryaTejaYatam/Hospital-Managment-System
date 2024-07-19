package com.HealthCare.HealthCareDoctor.Service.OtherServices.Staff;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HealthCare.HealthCareDoctor.Entity.Doctor;
import com.HealthCare.HealthCareDoctor.Model.PatientModel;
import com.HealthCare.HealthCareDoctor.Respository.DoctorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorServiceForStaffImp implements DoctorServiceForStaff {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void assignAndUpdatingDoctorToThePatient(PatientModel patientModel) {

        log.info("Finding the patient by Patient Id, Problem, and Appointment Date");
        Doctor existingPatient = doctorRepository.findByPatientIdAndProblemAndAppointmentDateAndDoctorId(
                patientModel.getPatientId(), patientModel.getProblem(), patientModel.getAppointmentDate(),
                patientModel.getDoctorId());

        if (existingPatient == null) {

            log.info("creating the row to store the deatils");
            Doctor doctor = Doctor.builder()
                    .age(patientModel.getAge())
                    .appointmentDate(patientModel.getAppointmentDate())
                    .appointmentStatus(patientModel.getAppointmentStatus())
                    .bloodGroup(patientModel.getBloodGroup())
                    .doctorId(patientModel.getDoctorId())
                    .doctorName(patientModel.getDoctorName())
                    .endingTime(patientModel.getEndingTime())
                    .firstName(patientModel.getFirstName())
                    .gender(patientModel.getGender())
                    .lastName(patientModel.getLastName())
                    .patientId(patientModel.getPatientId())
                    .problem(patientModel.getProblem())
                    .startingTime(patientModel.getStartingTime())
                    .phoneNumber(patientModel.getPhoneNumber())
                    .treatmentstatus(patientModel.getTreatmentstatus())
                    .build();

            // details are saved in the doctor table
            log.info("details are saved in the doctor table");
            doctorRepository.save(doctor);
        } else {

            existingPatient.setDoctorName(patientModel.getDoctorName());
            existingPatient.setEndingTime(patientModel.getEndingTime());
            existingPatient.setStartingTime(patientModel.getStartingTime());
            existingPatient.setDoctorId(patientModel.getDoctorId());

            log.info("saving the details after updating");
            doctorRepository.save(existingPatient);
        }
    }

    @Override
    public void cancelingTheAppointmnetByTheStaffOrByThePatinet(String patientId, String problem, LocalDate appointmentDate) {
        log.info("Finding the patient by Patient Id, Problem, and Appointment Date");
        Doctor existingPatient = doctorRepository.findByPatientIdAndProblemAndAppointmentDate(
                patientId, problem, appointmentDate);

        existingPatient.setDoctorName(null);
        existingPatient.setEndingTime(null);
        existingPatient.setStartingTime(null);
        existingPatient.setDoctorId(null);
        existingPatient.setTreatmentstatus("Cancled");

        log.info("saving the details after updating");
        doctorRepository.save(existingPatient);
    }

}
