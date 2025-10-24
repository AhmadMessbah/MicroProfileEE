package com.mftplus.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Person Data Transfer Object
 * Used for REST API communication between microservices
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class PersonDTO {
    
    private Long id;
    
    @NotBlank(message = "نام الزامی است")
    @Size(max = 50, message = "نام نمی‌تواند بیش از 50 کاراکتر باشد")
    private String firstName;
    
    @NotBlank(message = "نام خانوادگی الزامی است")
    @Size(max = 50, message = "نام خانوادگی نمی‌تواند بیش از 50 کاراکتر باشد")
    private String lastName;
    
    @Email(message = "ایمیل معتبر نیست")
    @Size(max = 100, message = "ایمیل نمی‌تواند بیش از 100 کاراکتر باشد")
    private String email;
    
    @Size(max = 15, message = "شماره تلفن نمی‌تواند بیش از 15 کاراکتر باشد")
    private String phoneNumber;
    
    @Size(max = 200, message = "آدرس نمی‌تواند بیش از 200 کاراکتر باشد")
    private String address;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor with required fields
    public PersonDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
