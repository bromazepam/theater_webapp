package com.theater.app.service;

import com.theater.app.domain.User;
import com.theater.app.domain.UserPayment;
import com.theater.app.domain.security.PasswordResetToken;
import com.theater.app.domain.security.UserRole;

import java.util.Set;

public interface UserService {

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    void createPasswordResetTokenForUser(final User user, final String token);

    PasswordResetToken getPasswordResetToken(final String token);

    User findById(Long id);

    void updateUserPayment(UserPayment userPayment, User user);

    void setUserDefaultPayment(Long userPaymentId, User user);
}
