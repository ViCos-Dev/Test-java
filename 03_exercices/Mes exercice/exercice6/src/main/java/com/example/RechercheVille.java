package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RechercheVille {

    private List<String> villes;

    public RechercheVille() {
        this.villes = Arrays.asList(
                "Paris", "Budapest", "Skopje", "Rotterdam", "Valence",
                "Vancouver", "Amsterdam", "Vienne", "Sydney", "New York",
                "Londres", "Bangkok", "Hong Kong", "Dubaï", "Rome", "Istanbul"
        );
    }

    public List<String> Rechercher(String mot) {
        if (mot.equals("*")) {
            return new ArrayList<>(villes);
        }
        if (mot.length() < 2) {
            throw new NotFoundException("Le texte de recherche doit contenir au moins 2 caractères");
        }
        return villes.stream()
                .filter(ville -> ville.toLowerCase().contains(mot.toLowerCase()))
                .collect(Collectors.toList());
    }
}
