package com.example;

import java.time.LocalDate;

public class Pret {
    private Adherent adherent;
    private Ouvrage ouvrage;
    private LocalDate dateDebut;
    private LocalDate dateRetourPrevue;

    public Pret(Adherent adherent, Ouvrage ouvrage, LocalDate dateDebut) {
        this.adherent = adherent;
        this.ouvrage = ouvrage;
        this.dateDebut = dateDebut;
        this.dateRetourPrevue = dateDebut.plusDays(21);
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
}
