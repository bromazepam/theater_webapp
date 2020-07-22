package com.theater.app.repository;

import com.theater.app.domain.Repertoire;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface RepertoireRepository extends CrudRepository<Repertoire, Long> {
    List<Repertoire> findByProjectionDate(Date projectionDate);
    List<Repertoire> findByProjectionDateGreaterThanEqual(Date currentDate);
}

