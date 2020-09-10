package com.theater.app.repository;

import com.theater.app.domain.Stage;
import org.springframework.data.repository.CrudRepository;

public interface StageRepository extends CrudRepository<Stage, String> {
}
