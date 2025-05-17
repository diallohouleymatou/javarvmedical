package com.ouly.rendezvous.dao;

import com.ouly.rendezvous.model.Patient;
import com.ouly.rendezvous.utils.EntityManagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class PatientDao {
    public Patient findByEmail(String email) {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Patient p WHERE p.email = :email", Patient.class)
                .setParameter("email", email)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    // Autres méthodes CRUD...
}

