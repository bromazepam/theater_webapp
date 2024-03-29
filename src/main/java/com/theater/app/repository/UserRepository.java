package com.theater.app.repository;

import com.theater.app.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);
}
