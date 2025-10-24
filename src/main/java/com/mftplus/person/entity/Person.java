package com.mftplus.person.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Person Entity - Represents a person in the system
 * Used for teaching Jakarta EE 10 with JTA transactions
 */
@Entity
@Table(name = "persons")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p ORDER BY p.lastName, p.firstName"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email"),
    @NamedQuery(name = "Person.findByPhone", query = "SELECT p FROM Person p WHERE p.phoneNumber = :phone")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"createdAt", "updatedAt"})
public class Person {
    
    private static final Logger logger = LoggerFactory.getLogger(Person.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "نام الزامی است")
    @Size(max = 50, message = "نام نمی‌تواند بیش از 50 کاراکتر باشد")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @NotBlank(message = "نام خانوادگی الزامی است")
    @Size(max = 50, message = "نام خانوادگی نمی‌تواند بیش از 50 کاراکتر باشد")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Email(message = "ایمیل معتبر نیست")
    @Size(max = 100, message = "ایمیل نمی‌تواند بیش از 100 کاراکتر باشد")
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @Size(max = 15, message = "شماره تلفن نمی‌تواند بیش از 15 کاراکتر باشد")
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    
    @Size(max = 200, message = "آدرس نمی‌تواند بیش از 200 کاراکتر باشد")
    @Column(name = "address", length = 200)
    private String address;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructor with required fields
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        logger.debug("Creating new person: {}", this);
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        logger.debug("Updating person: {}", this);
        updatedAt = LocalDateTime.now();
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
