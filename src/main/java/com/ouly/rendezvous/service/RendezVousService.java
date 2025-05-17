package com.ouly.rendezvous.service;

import com.ouly.rendezvous.model.RendezVous;
import com.ouly.rendezvous.model.Medecin;
import com.ouly.rendezvous.model.Patient;
import com.ouly.rendezvous.model.Specialite;
import com.ouly.rendezvous.utils.EntityManagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RendezVousService {
    public boolean reserverCreneau(Medecin medecin, Patient patient, LocalDateTime dateHeure) {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            RendezVous rv = new RendezVous();
            rv.setMedecin(medecin);
            rv.setPatient(patient);
            rv.setDateHeure(dateHeure);
            em.persist(rv);
            em.getTransaction().commit();
            return true;
        } catch (OptimisticLockException e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public List<Medecin> getAllMedecins() {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            TypedQuery<Medecin> query = em.createQuery("SELECT m FROM Medecin m", Medecin.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Patient> getAllPatients() {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p", Patient.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Specialite> getAllSpecialites() {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            TypedQuery<Specialite> query = em.createQuery("SELECT s FROM Specialite s", Specialite.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Medecin> getMedecinsBySpecialite(Specialite specialite) {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            TypedQuery<Medecin> query = em.createQuery("SELECT m FROM Medecin m WHERE m.specialite = :specialite", Medecin.class);
            query.setParameter("specialite", specialite);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public String reserverRendezVous(Medecin medecin, Patient patient, LocalDate date) {
        if (medecin == null || patient == null || date == null) {
            return "Tous les champs sont obligatoires.";
        }
        if (date.isBefore(LocalDate.now())) {
            return "La date doit être dans le futur.";
        }
        if (!isCreneauDisponible(medecin, date)) {
            return "Ce créneau n'est pas disponible pour ce médecin.";
        }
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            RendezVous rv = new RendezVous();
            rv.setMedecin(medecin);
            rv.setPatient(patient);
            rv.setDateHeure(date.atStartOfDay());
            em.persist(rv);
            em.getTransaction().commit();
            return "Rendez-vous réservé avec succès.";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return "Erreur lors de la réservation : " + e.getMessage();
        } finally {
            em.close();
        }
    }

    public boolean isCreneauDisponible(Medecin medecin, LocalDate date) {
        EntityManager em = EntityManagerUtils.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM RendezVous r WHERE r.medecin = :medecin AND r.dateHeure = :dateHeure",
                Long.class);
            query.setParameter("medecin", medecin);
            query.setParameter("dateHeure", date.atStartOfDay());
            return query.getSingleResult() == 0;
        } finally {
            em.close();
        }
    }
}
