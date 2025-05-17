package com.ouly.rendezvous.service;

import com.ouly.rendezvous.model.Patient;
import com.ouly.rendezvous.utils.EntityManagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PatientService {
    public String inscrirePatient(String nom, String email, String password, String confirmPassword) {
        if (nom == null || nom.isBlank() || email == null || email.isBlank() || password == null || password.isBlank() || confirmPassword == null || confirmPassword.isBlank()) {
            return "Tous les champs sont obligatoires.";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Email invalide.";
        }
        if (!password.equals(confirmPassword)) {
            return "Les mots de passe ne correspondent pas.";
        }
        if (password.length() < 6) {
            return "Le mot de passe doit contenir au moins 6 caractères.";
        }
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Patient p WHERE p.email = :email", Long.class);
            query.setParameter("email", email);
            if (query.getSingleResult() > 0) {
                return "Cet email est déjà utilisé.";
            }
            em.getTransaction().begin();
            Patient patient = new Patient();
            patient.setNom(nom);
            patient.setEmail(email);
            patient.setMotDePasse(password);
            em.persist(patient);
            em.getTransaction().commit();
            return "Inscription réussie ! Vous pouvez vous connecter.";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return "Erreur lors de l'inscription : " + e.getMessage();
        } finally {
            em.close();
        }
    }
}

