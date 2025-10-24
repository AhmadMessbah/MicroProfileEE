package com.mftplus.simcard.entity;

/**
 * Enum for SIM Card Status
 */
public enum SimCardStatus {
    ACTIVE("فعال"),
    INACTIVE("غیرفعال"),
    SUSPENDED("معلق"),
    EXPIRED("منقضی شده");
    
    private final String persianName;
    
    SimCardStatus(String persianName) {
        this.persianName = persianName;
    }
    
    public String getPersianName() {
        return persianName;
    }
}
