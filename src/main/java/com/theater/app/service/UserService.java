package com.theater.app.service;

import com.theater.app.domain.User;
import com.theater.app.domain.security.UserRole;

import java.util.Set;

public interface UserService {
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
}
