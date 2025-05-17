package com.ouly.rendezvous.concurrency;

import com.ouly.rendezvous.model.Medecin;
import com.ouly.rendezvous.model.Patient;
import com.ouly.rendezvous.service.RendezVousService;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReservationTask implements Runnable {
    private static final Lock lock = new ReentrantLock();
    private final RendezVousService service;
    private final Medecin medecin;
    private final Patient patient;
    private final LocalDateTime dateHeure;

    public ReservationTask(RendezVousService service, Medecin medecin, Patient patient, LocalDateTime dateHeure) {
        this.service = service;
        this.medecin = medecin;
        this.patient = patient;
        this.dateHeure = dateHeure;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            boolean success = service.reserverCreneau(medecin, patient, dateHeure);
            System.out.println(Thread.currentThread().getName() + " : " + (success ? "Succès" : "Échec") + " de réservation pour " + patient.getNom());
        } finally {
            lock.unlock();
        }
    }
}

