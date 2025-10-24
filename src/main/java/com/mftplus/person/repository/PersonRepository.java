package com.mftplus.person.repository;

import com.mftplus.person.entity.Person;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Person Repository - Data Access Layer for Person Entity
 * Uses JTA transactions and EntityManager for database operations
 */
@Stateless
public class PersonRepository {
    
    @PersistenceContext(unitName = "personPU")
    private EntityManager entityManager;
    
    /**
     * Save or update a person
     */
    public Person save(Person person) {
        if (person.getId() == null) {
            entityManager.persist(person);
            return person;
        } else {
            return entityManager.merge(person);
        }
    }
    
    /**
     * Find person by ID
     */
    public Optional<Person> findById(Long id) {
        Person person = entityManager.find(Person.class, id);
        return Optional.ofNullable(person);
    }
    
    /**
     * Find all persons
     */
    public List<Person> findAll() {
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findAll", Person.class);
        return query.getResultList();
    }
    
    /**
     * Find person by email
     */
    public Optional<Person> findByEmail(String email) {
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByEmail", Person.class);
        query.setParameter("email", email);
        List<Person> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    /**
     * Find person by phone number
     */
    public Optional<Person> findByPhoneNumber(String phoneNumber) {
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByPhone", Person.class);
        query.setParameter("phone", phoneNumber);
        List<Person> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    /**
     * Delete person by ID
     */
    public boolean deleteById(Long id) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            entityManager.remove(person);
            return true;
        }
        return false;
    }
    
    /**
     * Check if person exists by email
     */
    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(p) FROM Person p WHERE p.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
    
    /**
     * Count total persons
     */
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(p) FROM Person p", Long.class);
        return query.getSingleResult();
    }
    
    /**
     * Search persons by name (first name or last name)
     */
    public List<Person> searchByName(String name) {
        TypedQuery<Person> query = entityManager.createQuery(
            "SELECT p FROM Person p WHERE p.firstName LIKE :name OR p.lastName LIKE :name ORDER BY p.lastName, p.firstName", 
            Person.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}
