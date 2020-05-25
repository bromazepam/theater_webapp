package com.theater.app.repository;

import com.theater.app.domain.Play;
import org.springframework.data.repository.CrudRepository;

public interface PlayRepository extends CrudRepository<Play,Long> {
}
