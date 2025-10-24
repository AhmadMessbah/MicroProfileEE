package com.mftplus.simcard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * SimCard Entity - Represents a SIM card in the system
 * Used for teaching Jakarta EE 10 with JTA transactions
 */
@Entity
@Table(name = "simcards")
@NamedQueries({
    @NamedQuery(name = "SimCard.findAll", query = "SELECT s FROM SimCard s ORDER BY s.phoneNumber"),
    @NamedQuery(name = "SimCard.findByPhoneNumber", query = "SELECT s FROM SimCard s WHERE s.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "SimCard.findByIccid", query = "SELECT s FROM SimCard s WHERE s.iccid = :iccid"),
    @NamedQuery(name = "SimCard.findByPersonId", query = "SELECT s FROM SimCard s WHERE s.personId = :personId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"createdAt", "updatedAt"})
public class SimCard {
    
    private static final Logger logger = LoggerFactory.getLogger(SimCard.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "شماره تلفن الزامی است")
    @Pattern(regexp = "^09[0-9]{9}$", message = "شماره تلفن باید با 09 شروع شده و 11 رقم باشد")
    @Column(name = "phone_number", unique = true, nullable = false, length = 11)
    private String phoneNumber;
    
    @NotBlank(message = "شماره ICCID الزامی است")
    @Size(min = 19, max = 20, message = "شماره ICCID باید 19 یا 20 رقم باشد")
    @Column(name = "iccid", unique = true, nullable = false, length = 20)
    private String iccid;
    
    @Size(max = 50, message = "نام اپراتور نمی‌تواند بیش از 50 کاراکتر باشد")
    @Column(name = "operator", length = 50)
    private String operator;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SimCardStatus status = SimCardStatus.ACTIVE;
    
    @Column(name = "person_id")
    private Long personId; // Foreign key to Person entity
    
    @Column(name = "balance")
    private Double balance = 0.0;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructor with required fields
    public SimCard(String phoneNumber, String iccid) {
        this.phoneNumber = phoneNumber;
        this.iccid = iccid;
        this.createdAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        logger.debug("Creating new SIM card: {}", this);
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        logger.debug("Updating SIM card: {}", this);
        updatedAt = LocalDateTime.now();
    }
}

