package com.ouly.rendezvous.model;

import jakarta.persistence.*;

@Entity
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    private Specialite specialite;
    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Specialite getSpecialite() { return specialite; }
    public void setSpecialite(Specialite specialite) { this.specialite = specialite; }
}

