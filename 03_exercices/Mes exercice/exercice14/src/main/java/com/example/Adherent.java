package com.example;

public class Adherent {
    private String id;
    private String nom;
    private boolean suspendu;
    private int compteurRetardImportantAnnee;

    public Adherent(String id, String nom) {
        this.id = id;
        this.nom = nom;
        this.suspendu = false;
        this.compteurRetardImportantAnnee = 0;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public boolean isSuspendu() {
        return suspendu;
    }

    public void setSuspendu(boolean suspendu) {
        this.suspendu = suspendu;
    }

    public int getCompteurRetardImportantAnnee() {
        return compteurRetardImportantAnnee;
    }

    public void setCompteurRetardImportantAnnee(int compteur) {
        this.compteurRetardImportantAnnee = compteur;
    }

    public void incrementerRetardImportant() {
        this.compteurRetardImportantAnnee++;
    }
}
