package com.example;

public class Ouvrage {
    private String id;
    private String titre;
    private boolean disponible;

    public Ouvrage(String id, String titre) {
        this.id = id;
        this.titre = titre;
        this.disponible = true;
    }

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
