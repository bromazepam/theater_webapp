package com.theater.app.service.impl;

import com.theater.app.domain.Repertoire;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.service.RepertoireService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RepertoireServiceImpl implements RepertoireService {

    private RepertoireRepository repertoireRepository;

    public RepertoireServiceImpl(RepertoireRepository repertoireRepository) {
        this.repertoireRepository = repertoireRepository;
    }

    @Override
    public Repertoire save(Repertoire repertoire) {
        return repertoireRepository.save(repertoire);
    }

    @Override
    public List<Repertoire> findAll() {
        return (List<Repertoire>) repertoireRepository.findAll();
    }

    @Override
    public Repertoire findById(Long id) {
        Optional<Repertoire> optionalRepertoire = repertoireRepository.findById(id);

        if (!optionalRepertoire.isPresent()) {
            throw new NotFoundException("Stage not found, For ID value: " + id.toString());
        }
        return optionalRepertoire.get();
    }

    @Override
    public List<Repertoire> findByDate(Date date) {
        List<Repertoire> repertoires = repertoireRepository.findByProjectionDate(new java.sql.Date(date.getTime()));

        return repertoires;
    }

    @Override
    public void deleteById(Long id) {
        repertoireRepository.deleteById(id);
    }
}
