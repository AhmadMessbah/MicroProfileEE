package com.mftplus.simcard.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for SimCardStatus Enum
 */
@DisplayName("SimCardStatus Enum Tests")
class SimCardStatusTest {
    
    @Test
    @DisplayName("Should have correct Persian names")
    void shouldHaveCorrectPersianNames() {
        // Then
        assertThat(SimCardStatus.ACTIVE.getPersianName()).isEqualTo("فعال");
        assertThat(SimCardStatus.INACTIVE.getPersianName()).isEqualTo("غیرفعال");
        assertThat(SimCardStatus.SUSPENDED.getPersianName()).isEqualTo("معلق");
        assertThat(SimCardStatus.EXPIRED.getPersianName()).isEqualTo("منقضی شده");
    }
    
    @Test
    @DisplayName("Should have all expected values")
    void shouldHaveAllExpectedValues() {
        // When
        SimCardStatus[] values = SimCardStatus.values();
        
        // Then
        assertThat(values).hasSize(4);
        assertThat(values).containsExactly(
            SimCardStatus.ACTIVE,
            SimCardStatus.INACTIVE,
            SimCardStatus.SUSPENDED,
            SimCardStatus.EXPIRED
        );
    }
    
    @Test
    @DisplayName("Should parse string values correctly")
    void shouldParseStringValuesCorrectly() {
        // Then
        assertThat(SimCardStatus.valueOf("ACTIVE")).isEqualTo(SimCardStatus.ACTIVE);
        assertThat(SimCardStatus.valueOf("INACTIVE")).isEqualTo(SimCardStatus.INACTIVE);
        assertThat(SimCardStatus.valueOf("SUSPENDED")).isEqualTo(SimCardStatus.SUSPENDED);
        assertThat(SimCardStatus.valueOf("EXPIRED")).isEqualTo(SimCardStatus.EXPIRED);
    }
}
