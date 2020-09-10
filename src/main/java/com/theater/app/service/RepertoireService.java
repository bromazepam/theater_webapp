package com.theater.app.service;

import com.theater.app.domain.Repertoire;

import java.util.Date;
import java.util.List;

public interface RepertoireService {
    Repertoire save(Repertoire repertoire);

    List<Repertoire> findAll();

    Repertoire findById(String id);

    void deleteById(String id);

    List<Repertoire> findByDate(Date date);

    List<Repertoire> findByPresentOrFutureDate(Date date);

    int availableSeats(String id);
}
