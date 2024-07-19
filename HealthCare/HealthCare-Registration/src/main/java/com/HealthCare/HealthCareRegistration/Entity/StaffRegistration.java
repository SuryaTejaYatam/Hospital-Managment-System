package com.HealthCare.HealthCareRegistration.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StaffRegistration {
    

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffId;
    
    @NotBlank(message = "Enter your First Name")
    private String firstName;

    @NotBlank(message = "Enter your Last  Name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String reTypePassword;

    @Column(length = 10, nullable = false)
    @NotBlank(message = "User mobile required")
    private String phoneNumber;

}
