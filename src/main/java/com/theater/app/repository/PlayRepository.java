package com.theater.app.repository;

import com.theater.app.domain.Play;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayRepository extends CrudRepository<Play,String> {
    List<Play> findByCategory(String category);
}
