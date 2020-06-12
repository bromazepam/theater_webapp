package com.theater.app.service;

import com.theater.app.domain.Repertoire;

import java.util.Date;
import java.util.List;

public interface RepertoireService {
    Repertoire save(Repertoire repertoire);

    List<Repertoire> findAll();

    Repertoire findById(Long id);

    void deleteById(Long id);

    List<Repertoire> findByDate(Date date);
}
