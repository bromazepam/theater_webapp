package com.theater.app.repository;

import com.theater.app.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {
	Role findByname(String name);
}
