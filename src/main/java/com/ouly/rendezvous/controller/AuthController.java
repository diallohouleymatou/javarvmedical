package com.ouly.rendezvous.controller;

import com.ouly.rendezvous.dao.PatientDao;
import com.ouly.rendezvous.model.Patient;
import com.ouly.rendezvous.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private Label statusLabel;

    private final PatientDao patientDao = new PatientDao();

    @FXML
    private void goToAccueil() {
        String email = emailField.getText();
        String password = passwordField.getText();
        Patient patient = patientDao.findByEmail(email);
        if (patient != null && patient.getMotDePasse().equals(password)) {
            statusLabel.setText("Connexion réussie");
            try {
                Stage stage = (Stage) emailField.getScene().getWindow();
                HelloApplication.change(stage, "accueil-patient-view");
            } catch (Exception e) {
                statusLabel.setText("Erreur navigation : " + e.getMessage());
            }
        } else {
            statusLabel.setText("Identifiants invalides");
        }
    }

    @FXML
    private void onInscriptionClick() {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            HelloApplication.change(stage, "inscription-view");
        } catch (Exception e) {
            statusLabel.setText("Erreur navigation : " + e.getMessage());
        }
    }

    public void onConnexionClick(ActionEvent actionEvent) {
    }
}
