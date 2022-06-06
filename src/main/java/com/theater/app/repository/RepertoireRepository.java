package com.theater.app.repository;

import com.theater.app.domain.Repertoire;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RepertoireRepository extends CrudRepository<Repertoire, String> {
    List<Repertoire> findByProjectionDate(Date projectionDate);

    List<Repertoire> findByProjectionDateGreaterThanEqual(Date currentDate);

    List<Repertoire> findByStatusIsTrue();

    List<Repertoire> findByStatusIsFalse();
}

