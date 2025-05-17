package com.ouly.rendezvous;

import com.ouly.rendezvous.utils.EntityManagerUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        change(stage,"auth-view");
    }
    public static void change(Stage stage,String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(file+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Application du CSS global
        scene.getStylesheets().add(HelloApplication.class.getResource("style-hopital.css").toExternalForm());
        stage.setTitle("Rendez-vous médical");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Initialisation de la base de test
        EntityManager em = EntityManagerUtils.getEntityManager();
        em.getTransaction().begin();
        try {
            long nbSpec = (long) em.createQuery("SELECT COUNT(s) FROM Specialite s").getSingleResult();
            if (nbSpec == 0) {
                com.ouly.rendezvous.model.Specialite cardio = new com.ouly.rendezvous.model.Specialite();
                cardio.setNom("Cardiologie");
                com.ouly.rendezvous.model.Specialite dermato = new com.ouly.rendezvous.model.Specialite();
                dermato.setNom("Dermatologie");
                com.ouly.rendezvous.model.Specialite generaliste = new com.ouly.rendezvous.model.Specialite();
                generaliste.setNom("Médecine générale");
                em.persist(cardio);
                em.persist(dermato);
                em.persist(generaliste);
                com.ouly.rendezvous.model.Medecin m1 = new com.ouly.rendezvous.model.Medecin();
                m1.setNom("Dr Dupont");
                m1.setSpecialite(cardio);
                com.ouly.rendezvous.model.Medecin m2 = new com.ouly.rendezvous.model.Medecin();
                m2.setNom("Dr Martin");
                m2.setSpecialite(dermato);
                com.ouly.rendezvous.model.Medecin m3 = new com.ouly.rendezvous.model.Medecin();
                m3.setNom("Dr Bernard");
                m3.setSpecialite(generaliste);
                em.persist(m1);
                em.persist(m2);
                em.persist(m3);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();

        }
        launch();
    }

}

