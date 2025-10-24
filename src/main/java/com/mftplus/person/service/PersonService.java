package com.mftplus.person.service;

import com.mftplus.person.client.SimCardClient;
import com.mftplus.person.dto.PersonDTO;
import com.mftplus.person.entity.Person;
import com.mftplus.person.repository.PersonRepository;
import com.mftplus.simcard.dto.SimCardDTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Person Service - Business Logic Layer for Person Management
 * Uses JTA transactions and REST client for microservice communication
 */
@Slf4j
@Stateless
public class PersonService {
    
    @EJB
    private PersonRepository personRepository;
    
//    @Inject
    @RestClient
    private SimCardClient simCardClient;
    
    @Inject
    private Validator validator;
    
    /**
     * Create a new person with JTA transaction
     */
    @Transactional
    public PersonDTO createPerson(PersonDTO personDTO) {
        log.info("Creating new person: {}", personDTO);
        
        // Validate input
        validatePersonDTO(personDTO);
        
        // Check if email already exists
        if (personDTO.getEmail() != null && personRepository.existsByEmail(personDTO.getEmail())) {
            log.warn("Email already exists: {}", personDTO.getEmail());
            throw new IllegalArgumentException("ایمیل قبلاً ثبت شده است");
        }
        
        // Convert DTO to Entity
        Person person = convertToEntity(personDTO);
        
        // Save person
        Person savedPerson = personRepository.save(person);
        log.info("Person created successfully with ID: {}", savedPerson.getId());
        
        // Convert back to DTO
        return convertToDTO(savedPerson);
    }
    
    /**
     * Get person by ID
     */
    public Optional<PersonDTO> getPersonById(Long id) {
        return personRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Get all persons
     */
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update person with JTA transaction
     */
    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        // Validate input
        validatePersonDTO(personDTO);
        
        // Check if person exists
        Optional<Person> existingPerson = personRepository.findById(id);
        if (existingPerson.isEmpty()) {
            throw new IllegalArgumentException("شخص مورد نظر یافت نشد");
        }
        
        // Check email uniqueness (excluding current person)
        if (personDTO.getEmail() != null) {
            Optional<Person> personWithEmail = personRepository.findByEmail(personDTO.getEmail());
            if (personWithEmail.isPresent() && !personWithEmail.get().getId().equals(id)) {
                throw new IllegalArgumentException("ایمیل قبلاً ثبت شده است");
            }
        }
        
        // Update person
        Person person = existingPerson.get();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setAddress(personDTO.getAddress());
        
        Person updatedPerson = personRepository.save(person);
        return convertToDTO(updatedPerson);
    }
    
    /**
     * Delete person with JTA transaction
     */
    @Transactional
    public boolean deletePerson(Long id) {
        return personRepository.deleteById(id);
    }
    
    /**
     * Get person count
     */
    public long getPersonCount() {
        return personRepository.count();
    }
    
    /**
     * Search persons by name
     */
    public List<PersonDTO> searchPersonsByName(String name) {
        return personRepository.searchByName(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get SIM cards for a person
     */
    public List<SimCardDTO> getSimCardsForPerson(Long personId) {
        try {
            return simCardClient.getSimCardsByPersonId(personId);
        } catch (Exception e) {
            throw new RuntimeException("خطا در دریافت اطلاعات سیم‌کارت: " + e.getMessage());
        }
    }
    
    /**
     * Validate PersonDTO using Bean Validation
     */
    private void validatePersonDTO(PersonDTO personDTO) {
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("خطا در اعتبارسنجی: " + errorMessage);
        }
    }
    
    /**
     * Convert Person Entity to PersonDTO
     */
    private PersonDTO convertToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setEmail(person.getEmail());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setAddress(person.getAddress());
        dto.setCreatedAt(person.getCreatedAt());
        dto.setUpdatedAt(person.getUpdatedAt());
        return dto;
    }
    
    /**
     * Convert PersonDTO to Person Entity
     */
    private Person convertToEntity(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setEmail(dto.getEmail());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());
        return person;
    }
}
