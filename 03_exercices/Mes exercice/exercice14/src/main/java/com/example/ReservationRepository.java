package com.example;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByOuvrageId(String ouvrageId);
    void save(Reservation reservation);
}
