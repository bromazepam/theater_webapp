package com.theater.app.service.impl;

import com.theater.app.domain.User;
import com.theater.app.domain.UserPayment;
import com.theater.app.domain.security.PasswordResetToken;
import com.theater.app.domain.security.UserRole;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.PasswordResetTokenRepository;
import com.theater.app.repository.RoleRepository;
import com.theater.app.repository.UserPaymentRepository;
import com.theater.app.repository.UserRepository;
import com.theater.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserPaymentRepository userPaymentRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository, UserPaymentRepository userPaymentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
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

    @Override
    public User findById(String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new NotFoundException("user not found");
        }
        return userOptional.get();
    }

    @Override
    public void updateUserPayment(UserPayment userPayment, User user) {
        userPayment.setUser(user);
        userPayment.setDefaultPayment(true);
        user.getUserPaymentList().add(userPayment);
        save(user);
    }

    @Override
    public void setUserDefaultPayment(String userPaymentId, User user) {
        List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();

        for (UserPayment userPayment : userPaymentList) {
            if (userPayment.getId().equals(userPaymentId)) {
                userPayment.setDefaultPayment(true);
                userPaymentRepository.save(userPayment);
            } else {
                userPayment.setDefaultPayment(false);
                userPaymentRepository.save(userPayment);
            }
        }
    }
}
