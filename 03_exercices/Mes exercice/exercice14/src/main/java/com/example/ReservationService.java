package com.example;

import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final OuvrageRepository ouvrageRepository;

    public ReservationService(ReservationRepository reservationRepository, OuvrageRepository ouvrageRepository) {
        this.reservationRepository = reservationRepository;
        this.ouvrageRepository = ouvrageRepository;
    }

    public Reservation reserver(Adherent adherent, Ouvrage ouvrage) {
        if (adherent.isSuspendu()) {
            throw new AdherentSuspenduException("L'adhérent est suspendu");
        }
        if (ouvrage.isDisponible()) {
            throw new IllegalStateException("L'ouvrage est disponible, pas besoin de réservation");
        }

        Reservation reservation = new Reservation(adherent, ouvrage);
        reservationRepository.save(reservation);
        return reservation;
    }

    public Reservation traiterRetour(Ouvrage ouvrage) {
        List<Reservation> reservations = reservationRepository.findByOuvrageId(ouvrage.getId());
        if (!reservations.isEmpty()) {
            return reservations.get(0);
        }
        ouvrage.setDisponible(true);
        ouvrageRepository.save(ouvrage);
        return null;
    }
}
