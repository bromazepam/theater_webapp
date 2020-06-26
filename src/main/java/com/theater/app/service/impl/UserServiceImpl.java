package com.theater.app.service.impl;

import com.theater.app.domain.User;
import com.theater.app.domain.security.PasswordResetToken;
import com.theater.app.domain.security.UserRole;
import com.theater.app.repository.PasswordResetTokenRepository;
import com.theater.app.repository.RoleRepository;
import com.theater.app.repository.UserRepository;
import com.theater.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordResetTokenRepository passwordResetTokenRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
						   PasswordResetTokenRepository passwordResetTokenRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}

	@Override
	public User createUser(User user, Set<UserRole> userRoles){
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

}
