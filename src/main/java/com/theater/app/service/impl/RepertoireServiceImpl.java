package com.theater.app.service.impl;

import com.theater.app.domain.Repertoire;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.service.RepertoireService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepertoireServiceImpl implements RepertoireService {

    private RepertoireService repertoireService;

    public RepertoireServiceImpl(RepertoireService repertoireService) {
        this.repertoireService = repertoireService;
    }

    @Override
    public Repertoire save(Repertoire repertoire) {
        return repertoireService.save(repertoire);
    }

    @Override
    public List<Repertoire> findAll() {
        return repertoireService.findAll();
    }

    @Override
    public Repertoire findById(Long id) {
        Optional<Repertoire> optionalRepertoire = Optional.ofNullable(repertoireService.findById(id));

        if (!optionalRepertoire.isPresent()) {
            throw new NotFoundException("Stage not found, For ID value: " + id.toString());
        }
        return optionalRepertoire.get();
    }

    @Override
    public void deleteById(Long id) {
        repertoireService.deleteById(id);
    }
}
