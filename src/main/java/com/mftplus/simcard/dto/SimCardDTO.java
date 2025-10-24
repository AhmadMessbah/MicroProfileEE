package com.mftplus.simcard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * SimCard Data Transfer Object
 * Used for REST API communication between microservices
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class SimCardDTO {
    
    private Long id;
    
    @NotBlank(message = "شماره تلفن الزامی است")
    @Pattern(regexp = "^09[0-9]{9}$", message = "شماره تلفن باید با 09 شروع شده و 11 رقم باشد")
    private String phoneNumber;
    
    @NotBlank(message = "شماره ICCID الزامی است")
    @Size(min = 19, max = 20, message = "شماره ICCID باید 19 یا 20 رقم باشد")
    private String iccid;
    
    @Size(max = 50, message = "نام اپراتور نمی‌تواند بیش از 50 کاراکتر باشد")
    private String operator;
    
    private String status;
    private Long personId;
    private Double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor with required fields
    public SimCardDTO(String phoneNumber, String iccid) {
        this.phoneNumber = phoneNumber;
        this.iccid = iccid;
    }
}
