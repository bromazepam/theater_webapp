package com.theater.app.repository;

import com.theater.app.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, String> {
	Role findByName(String name);
}
