package com.ouly.rendezvous.concurrency;

import com.ouly.rendezvous.model.Medecin;
import com.ouly.rendezvous.model.Patient;
import com.ouly.rendezvous.service.RendezVousService;

import java.time.LocalDate;
import java.util.List;

public class ReservationThread extends Thread {
    private final Medecin medecin;
    private final Patient patient;
    private final LocalDate date;
    private String result;

    public ReservationThread(Medecin medecin, Patient patient, LocalDate date) {
        this.medecin = medecin;
        this.patient = patient;
        this.date = date;
    }

    @Override
    public void run() {
        try {
            Medecin m = new RendezVousService().getAllMedecins().get(0);
            List<Patient> ps = new RendezVousService().getAllPatients();
            Patient p = ps.get(0);
            Patient p1 = ps.get(1);
            new RendezVousService().reserverCreneau(m, p, date.atStartOfDay());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            result = "Réservation interrompue";
        }
    }

    public String getResult() {
        return result;
    }
}

