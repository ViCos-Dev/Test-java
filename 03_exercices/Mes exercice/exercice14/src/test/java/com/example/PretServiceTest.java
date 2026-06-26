package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PretServiceTest {

    private OuvrageRepository ouvrageRepository;
    private PretRepository pretRepository;
    private PretService pretService;

    @BeforeEach
    void setUp() {
        ouvrageRepository = mock(OuvrageRepository.class);
        pretRepository = mock(PretRepository.class);
        pretService = new PretService(ouvrageRepository, pretRepository);
    }

    @Test
    void creer_pret_retourne_pret_avec_date_retour_21_jours() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);

        Pret pret = pretService.creer(adherent, ouvrage, dateDebut);

        assertThat(pret.getDateRetourPrevue()).isEqualTo(dateDebut.plusDays(21));
    }

    @Test
    void creer_pret_ouvrage_indisponible_leve_exception() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        ouvrage.setDisponible(false);
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);

        assertThatThrownBy(() -> pretService.creer(adherent, ouvrage, dateDebut))
                .isInstanceOf(OuvrageIndisponibleException.class);
    }

    @Test
    void creer_pret_adherent_suspendu_leve_exception() {
        Adherent adherent = new Adherent("A1", "Alice");
        adherent.setSuspendu(true);
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);

        assertThatThrownBy(() -> pretService.creer(adherent, ouvrage, dateDebut))
                .isInstanceOf(AdherentSuspenduException.class);
    }

    @Test
    void creer_pret_met_ouvrage_indisponible() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);

        pretService.creer(adherent, ouvrage, dateDebut);

        assertThat(ouvrage.isDisponible()).isFalse();
    }

    @Test
    void calculer_penalite_retour_a_temps_retourne_zero() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);
        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        LocalDate dateRetour = LocalDate.of(2026, 1, 21);

        double penalite = pretService.calculerPenalite(pret, dateRetour);

        assertThat(penalite).isEqualTo(0.0);
    }

    @Test
    void calculer_penalite_retour_en_retard_calcule_correctement() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);
        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        // dateRetourPrevue = 22 jan, dateRetour = 5 fev = 14 jours de retard
        LocalDate dateRetour = LocalDate.of(2026, 2, 5);

        double penalite = pretService.calculerPenalite(pret, dateRetour);

        assertThat(penalite).isEqualTo(14 * 0.15);
    }

    @Test
    void retourner_ouvrage_rend_ouvrage_disponible() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        ouvrage.setDisponible(false);
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);
        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        LocalDate dateRetour = LocalDate.of(2026, 1, 20);

        pretService.retournerOuvrage(pret, dateRetour);

        assertThat(ouvrage.isDisponible()).isTrue();
        verify(ouvrageRepository).save(ouvrage);
    }

    @Test
    void retourner_ouvrage_avec_retard_important_incremente_compteur() {
        Adherent adherent = new Adherent("A1", "Alice");
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        ouvrage.setDisponible(false);
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);
        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        // 29 jours de retard > 7 jours donc retard important
        LocalDate dateRetour = LocalDate.of(2026, 2, 20);

        pretService.retournerOuvrage(pret, dateRetour);

        assertThat(adherent.getCompteurRetardImportantAnnee()).isEqualTo(1);
    }

    @Test
    void adherent_avec_trois_retards_importants_est_suspendu() {
        Adherent adherent = new Adherent("A1", "Alice");
        adherent.setCompteurRetardImportantAnnee(2);
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        ouvrage.setDisponible(false);
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);
        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        LocalDate dateRetour = LocalDate.of(2026, 2, 20);

        pretService.retournerOuvrage(pret, dateRetour);

        assertThat(adherent.isSuspendu()).isTrue();
    }

    @Test
    void retard_inferieur_a_7_jours_ne_suspend_pas_adherent() {
        Adherent adherent = new Adherent("A1", "Alice");
        adherent.setCompteurRetardImportantAnnee(2);
        Ouvrage ouvrage = new Ouvrage("O1", "Le Petit Prince");
        ouvrage.setDisponible(false);
        LocalDate dateDebut = LocalDate.of(2026, 1, 1);
        Pret pret = new Pret(adherent, ouvrage, dateDebut);
        // 3 jours de retard <= 7 jours donc pas important
        LocalDate dateRetour = LocalDate.of(2026, 1, 25);

        pretService.retournerOuvrage(pret, dateRetour);

        assertThat(adherent.isSuspendu()).isFalse();
        assertThat(adherent.getCompteurRetardImportantAnnee()).isEqualTo(2);
    }
}
