package com.mftplus.simcard.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Unit tests for SimCard Entity
 */
@DisplayName("SimCard Entity Tests")
class SimCardTest {
    
    private SimCard simCard;
    
    @BeforeEach
    void setUp() {
        simCard = SimCard.builder()
                .phoneNumber("09123456789")
                .iccid("12345678901234567890")
                .operator("ایرانسل")
                .status(SimCardStatus.ACTIVE)
                .personId(1L)
                .balance(10000.0)
                .build();
    }
    
    @Test
    @DisplayName("Should create SIM card with required fields")
    void shouldCreateSimCardWithRequiredFields() {
        // Given
        SimCard newSimCard = new SimCard("09987654321", "09876543210987654321");
        
        // Then
        assertThat(newSimCard.getPhoneNumber()).isEqualTo("09987654321");
        assertThat(newSimCard.getIccid()).isEqualTo("09876543210987654321");
        assertThat(newSimCard.getCreatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("Should set createdAt and updatedAt on persist")
    void shouldSetTimestampsOnPersist() {
        // Given
        SimCard newSimCard = SimCard.builder()
                .phoneNumber("09111111111")
                .iccid("11111111111111111111")
                .build();
        
        // When
        newSimCard.onCreate();
        
        // Then
        assertThat(newSimCard.getCreatedAt()).isNotNull();
        assertThat(newSimCard.getUpdatedAt()).isNotNull();
        assertThat(newSimCard.getCreatedAt()).isEqualTo(newSimCard.getUpdatedAt());
    }
    
    @Test
    @DisplayName("Should update updatedAt on update")
    void shouldUpdateTimestampOnUpdate() throws InterruptedException {
        // Given
        simCard.onCreate();
        LocalDateTime originalUpdatedAt = simCard.getUpdatedAt();
        
        // When
        Thread.sleep(1); // Ensure time difference
        simCard.onUpdate();
        
        // Then
        assertThat(simCard.getUpdatedAt()).isAfter(originalUpdatedAt);
    }
    
    @Test
    @DisplayName("Should have correct equals and hashCode")
    void shouldHaveCorrectEqualsAndHashCode() {
        // Given
        SimCard simCard1 = SimCard.builder().id(1L).phoneNumber("09111111111").iccid("11111111111111111111").build();
        SimCard simCard2 = SimCard.builder().id(1L).phoneNumber("09222222222").iccid("22222222222222222222").build();
        SimCard simCard3 = SimCard.builder().id(2L).phoneNumber("09111111111").iccid("11111111111111111111").build();
        
        // Then
        assertThat(simCard1).isEqualTo(simCard2);
        assertThat(simCard1).isNotEqualTo(simCard3);
        assertThat(simCard1.hashCode()).isEqualTo(simCard2.hashCode());
        assertThat(simCard1.hashCode()).isNotEqualTo(simCard3.hashCode());
    }
    
    @Test
    @DisplayName("Should have correct toString")
    void shouldHaveCorrectToString() {
        // When
        String toString = simCard.toString();
        
        // Then
        assertThat(toString).contains("09123456789");
        assertThat(toString).contains("12345678901234567890");
        assertThat(toString).contains("ایرانسل");
        assertThat(toString).contains("ACTIVE");
        assertThat(toString).doesNotContain("createdAt");
        assertThat(toString).doesNotContain("updatedAt");
    }
    
    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // Given
        SimCard simCardWithNulls = SimCard.builder()
                .phoneNumber("09111111111")
                .iccid("11111111111111111111")
                .operator(null)
                .personId(null)
                .balance(null)
                .build();
        
        // Then
        assertThat(simCardWithNulls.getOperator()).isNull();
        assertThat(simCardWithNulls.getPersonId()).isNull();
        assertThat(simCardWithNulls.getBalance()).isNull();
    }
    
    @Test
    @DisplayName("Should set default values correctly")
    void shouldSetDefaultValuesCorrectly() {
        // Given
        SimCard newSimCard = new SimCard("09111111111", "11111111111111111111");
        
        // Then
        assertThat(newSimCard.getStatus()).isEqualTo(SimCardStatus.ACTIVE);
        assertThat(newSimCard.getBalance()).isEqualTo(0.0);
    }
}
