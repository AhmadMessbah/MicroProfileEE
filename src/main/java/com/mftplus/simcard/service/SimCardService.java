package com.mftplus.simcard.service;

import com.mftplus.simcard.client.PersonClient;
import com.mftplus.simcard.dto.SimCardDTO;
import com.mftplus.simcard.entity.SimCard;
import com.mftplus.simcard.entity.SimCardStatus;
import com.mftplus.simcard.repository.SimCardRepository;
import com.mftplus.person.dto.PersonDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
//import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SimCard Service - Business Logic Layer for SimCard Management
 * Uses JTA transactions and REST client for microservice communication
 */
@Slf4j
@Stateless
public class SimCardService {
    
    @EJB
    private SimCardRepository simCardRepository;
    
//    @Inject
    @RestClient
    private PersonClient personClient;
    
    @Inject
    private Validator validator;
    
    /**
     * Create a new SIM card with JTA transaction
     */
    @Transactional
    public SimCardDTO createSimCard(SimCardDTO simCardDTO) {
        // Validate input
        validateSimCardDTO(simCardDTO);
        
        // Check if phone number already exists
        if (simCardRepository.existsByPhoneNumber(simCardDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("شماره تلفن قبلاً ثبت شده است");
        }
        
        // Check if ICCID already exists
        if (simCardRepository.existsByIccid(simCardDTO.getIccid())) {
            throw new IllegalArgumentException("شماره ICCID قبلاً ثبت شده است");
        }
        
        // Verify person exists if personId is provided
        if (simCardDTO.getPersonId() != null) {
            try {
                PersonDTO person = personClient.getPersonById(simCardDTO.getPersonId());
                if (person == null) {
                    throw new IllegalArgumentException("شخص مورد نظر یافت نشد");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("خطا در بررسی شخص: " + e.getMessage());
            }
        }
        
        // Convert DTO to Entity
        SimCard simCard = convertToEntity(simCardDTO);
        
        // Save SIM card
        SimCard savedSimCard = simCardRepository.save(simCard);
        
        // Convert back to DTO
        return convertToDTO(savedSimCard);
    }
    
    /**
     * Get SIM card by ID
     */
    public Optional<SimCardDTO> getSimCardById(Long id) {
        return simCardRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Get all SIM cards
     */
    public List<SimCardDTO> getAllSimCards() {
        return simCardRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update SIM card with JTA transaction
     */
    @Transactional
    public SimCardDTO updateSimCard(Long id, SimCardDTO simCardDTO) {
        // Validate input
        validateSimCardDTO(simCardDTO);
        
        // Check if SIM card exists
        Optional<SimCard> existingSimCard = simCardRepository.findById(id);
        if (existingSimCard.isEmpty()) {
            throw new IllegalArgumentException("سیم‌کارت مورد نظر یافت نشد");
        }
        
        // Check phone number uniqueness (excluding current SIM card)
        Optional<SimCard> simCardWithPhone = simCardRepository.findByPhoneNumber(simCardDTO.getPhoneNumber());
        if (simCardWithPhone.isPresent() && !simCardWithPhone.get().getId().equals(id)) {
            throw new IllegalArgumentException("شماره تلفن قبلاً ثبت شده است");
        }
        
        // Check ICCID uniqueness (excluding current SIM card)
        Optional<SimCard> simCardWithIccid = simCardRepository.findByIccid(simCardDTO.getIccid());
        if (simCardWithIccid.isPresent() && !simCardWithIccid.get().getId().equals(id)) {
            throw new IllegalArgumentException("شماره ICCID قبلاً ثبت شده است");
        }
        
        // Verify person exists if personId is provided
        if (simCardDTO.getPersonId() != null) {
            try {
                PersonDTO person = personClient.getPersonById(simCardDTO.getPersonId());
                if (person == null) {
                    throw new IllegalArgumentException("شخص مورد نظر یافت نشد");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("خطا در بررسی شخص: " + e.getMessage());
            }
        }
        
        // Update SIM card
        SimCard simCard = existingSimCard.get();
        simCard.setPhoneNumber(simCardDTO.getPhoneNumber());
        simCard.setIccid(simCardDTO.getIccid());
        simCard.setOperator(simCardDTO.getOperator());
        simCard.setStatus(SimCardStatus.valueOf(simCardDTO.getStatus()));
        simCard.setPersonId(simCardDTO.getPersonId());
        simCard.setBalance(simCardDTO.getBalance());
        
        SimCard updatedSimCard = simCardRepository.save(simCard);
        return convertToDTO(updatedSimCard);
    }
    
    /**
     * Delete SIM card with JTA transaction
     */
    @Transactional
    public boolean deleteSimCard(Long id) {
        return simCardRepository.deleteById(id);
    }
    
    /**
     * Get SIM card count
     */
    public long getSimCardCount() {
        return simCardRepository.count();
    }
    
    /**
     * Get SIM cards by person ID
     */
    public List<SimCardDTO> getSimCardsByPersonId(Long personId) {
        return simCardRepository.findByPersonId(personId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update SIM card balance
     */
    @Transactional
    public boolean updateBalance(Long id, Double newBalance) {
        return simCardRepository.updateBalance(id, newBalance);
    }
    
    /**
     * Get SIM cards by operator
     */
    public List<SimCardDTO> getSimCardsByOperator(String operator) {
        return simCardRepository.findByOperator(operator)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Validate SimCardDTO using Bean Validation
     */
    private void validateSimCardDTO(SimCardDTO simCardDTO) {
        Set<ConstraintViolation<SimCardDTO>> violations = validator.validate(simCardDTO);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("خطا در اعتبارسنجی: " + errorMessage);
        }
    }
    
    /**
     * Convert SimCard Entity to SimCardDTO
     */
    private SimCardDTO convertToDTO(SimCard simCard) {
        SimCardDTO dto = new SimCardDTO();
        dto.setId(simCard.getId());
        dto.setPhoneNumber(simCard.getPhoneNumber());
        dto.setIccid(simCard.getIccid());
        dto.setOperator(simCard.getOperator());
        dto.setStatus(simCard.getStatus().name());
        dto.setPersonId(simCard.getPersonId());
        dto.setBalance(simCard.getBalance());
        dto.setCreatedAt(simCard.getCreatedAt());
        dto.setUpdatedAt(simCard.getUpdatedAt());
        return dto;
    }
    
    /**
     * Convert SimCardDTO to SimCard Entity
     */
    private SimCard convertToEntity(SimCardDTO dto) {
        SimCard simCard = new SimCard();
        simCard.setId(dto.getId());
        simCard.setPhoneNumber(dto.getPhoneNumber());
        simCard.setIccid(dto.getIccid());
        simCard.setOperator(dto.getOperator());
        simCard.setStatus(SimCardStatus.valueOf(dto.getStatus()));
        simCard.setPersonId(dto.getPersonId());
        simCard.setBalance(dto.getBalance());
        return simCard;
    }
}
