package com.mftplus.person.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for PersonDTO
 */
@DisplayName("PersonDTO Tests")
class PersonDTOTest {
    
    private PersonDTO personDTO;
    
    @BeforeEach
    void setUp() {
        personDTO = PersonDTO.builder()
                .id(1L)
                .firstName("احمد")
                .lastName("احمدی")
                .email("ahmad@example.com")
                .phoneNumber("09123456789")
                .address("تهران، ایران")
                .build();
    }
    
    @Test
    @DisplayName("Should create PersonDTO with required fields")
    void shouldCreatePersonDTOWithRequiredFields() {
        // Given
        PersonDTO newPersonDTO = new PersonDTO("علی", "علی‌زاده");
        
        // Then
        assertThat(newPersonDTO.getFirstName()).isEqualTo("علی");
        assertThat(newPersonDTO.getLastName()).isEqualTo("علی‌زاده");
    }
    
    @Test
    @DisplayName("Should return full name correctly")
    void shouldReturnFullNameCorrectly() {
        // When
        String fullName = personDTO.getFullName();
        
        // Then
        assertThat(fullName).isEqualTo("احمد احمدی");
    }
    
    @Test
    @DisplayName("Should have correct equals and hashCode")
    void shouldHaveCorrectEqualsAndHashCode() {
        // Given
        PersonDTO personDTO1 = PersonDTO.builder().id(1L).firstName("علی").lastName("علی‌زاده").build();
        PersonDTO personDTO2 = PersonDTO.builder().id(1L).firstName("محمد").lastName("محمدی").build();
        PersonDTO personDTO3 = PersonDTO.builder().id(2L).firstName("علی").lastName("علی‌زاده").build();
        
        // Then
        assertThat(personDTO1).isEqualTo(personDTO2);
        assertThat(personDTO1).isNotEqualTo(personDTO3);
        assertThat(personDTO1.hashCode()).isEqualTo(personDTO2.hashCode());
        assertThat(personDTO1.hashCode()).isNotEqualTo(personDTO3.hashCode());
    }
    
    @Test
    @DisplayName("Should have correct toString")
    void shouldHaveCorrectToString() {
        // When
        String toString = personDTO.toString();
        
        // Then
        assertThat(toString).contains("احمد");
        assertThat(toString).contains("احمدی");
        assertThat(toString).contains("ahmad@example.com");
        assertThat(toString).contains("09123456789");
    }
    
    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // Given
        PersonDTO personDTOWithNulls = PersonDTO.builder()
                .id(1L)
                .firstName("علی")
                .lastName("علی‌زاده")
                .email(null)
                .phoneNumber(null)
                .address(null)
                .build();
        
        // Then
        assertThat(personDTOWithNulls.getEmail()).isNull();
        assertThat(personDTOWithNulls.getPhoneNumber()).isNull();
        assertThat(personDTOWithNulls.getAddress()).isNull();
        assertThat(personDTOWithNulls.getFullName()).isEqualTo("علی علی‌زاده");
    }
    
    @Test
    @DisplayName("Should use builder pattern correctly")
    void shouldUseBuilderPatternCorrectly() {
        // When
        PersonDTO builtPerson = PersonDTO.builder()
                .id(2L)
                .firstName("محمد")
                .lastName("محمدی")
                .email("mohammad@example.com")
                .phoneNumber("09987654321")
                .address("اصفهان، ایران")
                .build();
        
        // Then
        assertThat(builtPerson.getId()).isEqualTo(2L);
        assertThat(builtPerson.getFirstName()).isEqualTo("محمد");
        assertThat(builtPerson.getLastName()).isEqualTo("محمدی");
        assertThat(builtPerson.getEmail()).isEqualTo("mohammad@example.com");
        assertThat(builtPerson.getPhoneNumber()).isEqualTo("09987654321");
        assertThat(builtPerson.getAddress()).isEqualTo("اصفهان، ایران");
    }
}
