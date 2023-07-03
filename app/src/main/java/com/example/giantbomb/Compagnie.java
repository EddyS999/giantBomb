package com.example.giantbomb;

public class Compagnie {
    private String nom;
    private String dateCreation;
    private String logo;
    private String description;

    // Constructeur
    public Compagnie(String nom, String dateCreation, String logo, String description) {
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.logo = logo;
        this.description = description;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Compagnie{" +
                "nom='" + nom + '\'' +
                ", dateCreation='" + dateCreation + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

