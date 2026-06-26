package com.example;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationSteps {

    private ReservationRepository reservationRepository;
    private OuvrageRepository ouvrageRepository;
    private ReservationService reservationService;

    private Adherent adherent;
    private Ouvrage ouvrage;
    private Reservation reservation;
    private Reservation returnedReservation;
    private Exception exception;
    private List<Reservation> reservationsList;

    @Before
    public void setUp() {
        reservationsList = new ArrayList<>();
        reservationRepository = mock(ReservationRepository.class);
        ouvrageRepository = mock(OuvrageRepository.class);
        reservationService = new ReservationService(reservationRepository, ouvrageRepository);

        doAnswer(inv -> {
            reservationsList.add(inv.getArgument(0));
            return null;
        }).when(reservationRepository).save(any(Reservation.class));

        when(reservationRepository.findByOuvrageId(anyString()))
                .thenAnswer(inv -> {
                    String id = inv.getArgument(0);
                    return reservationsList.stream()
                            .filter(r -> r.getOuvrage().getId().equals(id))
                            .collect(Collectors.toList());
                });

        reservation = null;
        returnedReservation = null;
        exception = null;
    }

    @Given("un adhérent {string} non suspendu")
    public void unAdherentNonSuspendu(String nom) {
        adherent = new Adherent(nom, nom);
    }

    @Given("un adhérent {string} suspendu")
    public void unAdherentSuspendu(String nom) {
        adherent = new Adherent(nom, nom);
        adherent.setSuspendu(true);
    }

    @Given("un ouvrage {string} indisponible")
    public void unOuvrageIndisponible(String titre) {
        ouvrage = new Ouvrage(titre, titre);
        ouvrage.setDisponible(false);
    }

    @Given("un ouvrage {string} disponible")
    public void unOuvrageDisponible(String titre) {
        ouvrage = new Ouvrage(titre, titre);
    }

    @Given("l'adhérent {string} a déjà réservé cet ouvrage")
    public void adherentADejaReserveCetOuvrage(String nom) {
        Adherent a = new Adherent(nom, nom);
        reservationsList.add(new Reservation(a, ouvrage));
    }

    @When("l'adhérent réserve l'ouvrage")
    public void adherentReserveLOuvrage() {
        try {
            reservation = reservationService.reserver(adherent, ouvrage);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("{string} réserve cet ouvrage")
    public void personneReserveCetOuvrage(String nom) {
        Adherent a = new Adherent(nom, nom);
        try {
            reservationService.reserver(a, ouvrage);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("l'ouvrage est rendu")
    public void lOuvrageEstRendu() {
        returnedReservation = reservationService.traiterRetour(ouvrage);
    }

    @Then("la réservation est créée avec succès")
    public void laReservationEstCreeeAvecSucces() {
        assertNotNull(reservation);
        assertNull(exception);
    }

    @Then("la réservation est refusée pour suspension")
    public void laReservationEstRefuseePourSuspension() {
        assertNotNull(exception);
        assertInstanceOf(AdherentSuspenduException.class, exception);
    }

    @Then("il y a {int} réservations en attente")
    public void ilYAReservationsEnAttente(int count) {
        assertEquals(count, reservationsList.size());
    }

    @Then("la réservation de {string} est retournée")
    public void laReservationDeEstRetournee(String nom) {
        assertNotNull(returnedReservation);
        assertEquals(nom, returnedReservation.getAdherent().getNom());
    }

    @Then("la réservation est refusée car l'ouvrage est disponible")
    public void laReservationEstRefuseePourOuvrageDisponible() {
        assertNotNull(exception);
        assertInstanceOf(IllegalStateException.class, exception);
    }
}
