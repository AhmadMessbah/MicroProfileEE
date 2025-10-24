package com.mftplus.person.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;
import static java.time.temporal.ChronoUnit.MILLIS;

import java.time.LocalDateTime;

/**
 * Unit tests for Person Entity
 */
@DisplayName("Person Entity Tests")
class PersonTest {
    
    private Person person;
    
    @BeforeEach
    void setUp() {
        person = Person.builder()
                .firstName("احمد")
                .lastName("احمدی")
                .email("ahmad@example.com")
                .phoneNumber("09123456789")
                .address("تهران، ایران")
                .build();
    }
    
    @Test
    @DisplayName("Should create person with required fields")
    void shouldCreatePersonWithRequiredFields() {
        // Given
        Person newPerson = new Person("علی", "علی‌زاده");
        
        // Then
        assertThat(newPerson.getFirstName()).isEqualTo("علی");
        assertThat(newPerson.getLastName()).isEqualTo("علی‌زاده");
        assertThat(newPerson.getCreatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("Should return full name correctly")
    void shouldReturnFullNameCorrectly() {
        // When
        String fullName = person.getFullName();
        
        // Then
        assertThat(fullName).isEqualTo("احمد احمدی");
    }
    
    @Test
    @DisplayName("Should set createdAt and updatedAt on persist")
    void shouldSetTimestampsOnPersist() {
        // Given
        Person newPerson = Person.builder()
                .firstName("محمد")
                .lastName("محمدی")
                .build();
        
        // When
        newPerson.onCreate();
        
        // Then
        assertThat(newPerson.getCreatedAt()).isNotNull();
        assertThat(newPerson.getUpdatedAt()).isNotNull();
        // Allow small time difference due to execution timing
        assertThat(newPerson.getCreatedAt()).isCloseTo(newPerson.getUpdatedAt(), within(1, ChronoUnit.MILLIS));
    }
    
    @Test
    @DisplayName("Should update updatedAt on update")
    void shouldUpdateTimestampOnUpdate() throws InterruptedException {
        // Given
        person.onCreate();
        LocalDateTime originalUpdatedAt = person.getUpdatedAt();
        
        // When
        Thread.sleep(1); // Ensure time difference
        person.onUpdate();
        
        // Then
        assertThat(person.getUpdatedAt()).isAfter(originalUpdatedAt);
    }
    
    @Test
    @DisplayName("Should have correct equals and hashCode")
    void shouldHaveCorrectEqualsAndHashCode() {
        // Given
        Person person1 = Person.builder().id(1L).firstName("علی").lastName("علی‌زاده").build();
        Person person2 = Person.builder().id(1L).firstName("محمد").lastName("محمدی").build();
        Person person3 = Person.builder().id(2L).firstName("علی").lastName("علی‌زاده").build();
        
        // Then
        assertThat(person1).isEqualTo(person2);
        assertThat(person1).isNotEqualTo(person3);
        assertThat(person1.hashCode()).isEqualTo(person2.hashCode());
        assertThat(person1.hashCode()).isNotEqualTo(person3.hashCode());
    }
    
    @Test
    @DisplayName("Should have correct toString")
    void shouldHaveCorrectToString() {
        // When
        String toString = person.toString();
        
        // Then
        assertThat(toString).contains("احمد");
        assertThat(toString).contains("احمدی");
        assertThat(toString).contains("ahmad@example.com");
        assertThat(toString).doesNotContain("createdAt");
        assertThat(toString).doesNotContain("updatedAt");
    }
    
    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // Given
        Person personWithNulls = Person.builder()
                .firstName("علی")
                .lastName("علی‌زاده")
                .email(null)
                .phoneNumber(null)
                .address(null)
                .build();
        
        // Then
        assertThat(personWithNulls.getEmail()).isNull();
        assertThat(personWithNulls.getPhoneNumber()).isNull();
        assertThat(personWithNulls.getAddress()).isNull();
        assertThat(personWithNulls.getFullName()).isEqualTo("علی علی‌زاده");
    }
}
