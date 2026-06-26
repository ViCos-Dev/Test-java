package com.example;

public class Reservation {
    private Adherent adherent;
    private Ouvrage ouvrage;

    public Reservation(Adherent adherent, Ouvrage ouvrage) {
        this.adherent = adherent;
        this.ouvrage = ouvrage;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }
}
