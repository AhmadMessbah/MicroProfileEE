package com.mftplus.simcard.repository;

import com.mftplus.simcard.entity.SimCard;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * SimCard Repository - Data Access Layer for SimCard Entity
 * Uses JTA transactions and EntityManager for database operations
 */
@Stateless
public class SimCardRepository {
    
    @PersistenceContext(unitName = "simcardPU")
    private EntityManager entityManager;
    
    /**
     * Save or update a SIM card
     */
    public SimCard save(SimCard simCard) {
        if (simCard.getId() == null) {
            entityManager.persist(simCard);
            return simCard;
        } else {
            return entityManager.merge(simCard);
        }
    }
    
    /**
     * Find SIM card by ID
     */
    public Optional<SimCard> findById(Long id) {
        SimCard simCard = entityManager.find(SimCard.class, id);
        return Optional.ofNullable(simCard);
    }
    
    /**
     * Find all SIM cards
     */
    public List<SimCard> findAll() {
        TypedQuery<SimCard> query = entityManager.createNamedQuery("SimCard.findAll", SimCard.class);
        return query.getResultList();
    }
    
    /**
     * Find SIM card by phone number
     */
    public Optional<SimCard> findByPhoneNumber(String phoneNumber) {
        TypedQuery<SimCard> query = entityManager.createNamedQuery("SimCard.findByPhoneNumber", SimCard.class);
        query.setParameter("phoneNumber", phoneNumber);
        List<SimCard> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    /**
     * Find SIM card by ICCID
     */
    public Optional<SimCard> findByIccid(String iccid) {
        TypedQuery<SimCard> query = entityManager.createNamedQuery("SimCard.findByIccid", SimCard.class);
        query.setParameter("iccid", iccid);
        List<SimCard> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    /**
     * Find SIM cards by person ID
     */
    public List<SimCard> findByPersonId(Long personId) {
        TypedQuery<SimCard> query = entityManager.createNamedQuery("SimCard.findByPersonId", SimCard.class);
        query.setParameter("personId", personId);
        return query.getResultList();
    }
    
    /**
     * Delete SIM card by ID
     */
    public boolean deleteById(Long id) {
        SimCard simCard = entityManager.find(SimCard.class, id);
        if (simCard != null) {
            entityManager.remove(simCard);
            return true;
        }
        return false;
    }
    
    /**
     * Check if SIM card exists by phone number
     */
    public boolean existsByPhoneNumber(String phoneNumber) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(s) FROM SimCard s WHERE s.phoneNumber = :phoneNumber", Long.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult() > 0;
    }
    
    /**
     * Check if SIM card exists by ICCID
     */
    public boolean existsByIccid(String iccid) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(s) FROM SimCard s WHERE s.iccid = :iccid", Long.class);
        query.setParameter("iccid", iccid);
        return query.getSingleResult() > 0;
    }
    
    /**
     * Count total SIM cards
     */
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(s) FROM SimCard s", Long.class);
        return query.getSingleResult();
    }
    
    /**
     * Find SIM cards by operator
     */
    public List<SimCard> findByOperator(String operator) {
        TypedQuery<SimCard> query = entityManager.createQuery(
            "SELECT s FROM SimCard s WHERE s.operator = :operator ORDER BY s.phoneNumber", 
            SimCard.class);
        query.setParameter("operator", operator);
        return query.getResultList();
    }
    
    /**
     * Update SIM card balance
     */
    public boolean updateBalance(Long id, Double newBalance) {
        SimCard simCard = entityManager.find(SimCard.class, id);
        if (simCard != null) {
            simCard.setBalance(newBalance);
            entityManager.merge(simCard);
            return true;
        }
        return false;
    }
}
