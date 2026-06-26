package com.example;

import java.time.LocalDate;

public class PretService {
    private final OuvrageRepository ouvrageRepository;
    private final PretRepository pretRepository;

    public PretService(OuvrageRepository ouvrageRepository, PretRepository pretRepository) {
        this.ouvrageRepository = ouvrageRepository;
        this.pretRepository = pretRepository;
    }

    public Pret creer(Adherent adherent, Ouvrage ouvrage, LocalDate dateDebut) {
        if (adherent.isSuspendu()) {
            throw new AdherentSuspenduException("L'adhérent est suspendu");
        }
        if (!ouvrage.isDisponible()) {
            throw new OuvrageIndisponibleException("L'ouvrage est indisponible");
        }

        ouvrage.setDisponible(false);
        ouvrageRepository.save(ouvrage);

        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        pretRepository.save(pret);
        return pret;
    }

    public double calculerPenalite(Pret pret, LocalDate dateRetour) {
        if (dateRetour.isAfter(pret.getDateRetourPrevue())) {
            long joursRetard = dateRetour.toEpochDay() - pret.getDateRetourPrevue().toEpochDay();
            return joursRetard * 0.15;
        }
        return 0;
    }

    public void retournerOuvrage(Pret pret, LocalDate dateRetour) {
        Ouvrage ouvrage = pret.getOuvrage();
        ouvrage.setDisponible(true);
        ouvrageRepository.save(ouvrage);

        if (dateRetour.isAfter(pret.getDateRetourPrevue())) {
            long joursRetard = dateRetour.toEpochDay() - pret.getDateRetourPrevue().toEpochDay();
            if (joursRetard > 7) {
                Adherent adherent = pret.getAdherent();
                adherent.incrementerRetardImportant();
                if (adherent.getCompteurRetardImportantAnnee() >= 3) {
                    adherent.setSuspendu(true);
                }
            }
        }
    }
}
