package com.theater.app.service.impl;

import com.theater.app.domain.User;
import com.theater.app.domain.UserPayment;
import com.theater.app.domain.security.Role;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.PasswordResetTokenRepository;
import com.theater.app.repository.UserPaymentRepository;
import com.theater.app.repository.UserRepository;
import com.theater.app.security.SecurityUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Spy
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Mock
    UserPaymentRepository userPaymentRepository;

    @Test
    void createUser() {
        //given
        User user = new User();
        user.setId("0");
        user.setUsername("admin");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user.setEmail("admin@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        Role role = new Role();
        role.setRoleId("0");
        role.setName("ROLE_ADMIN");
        userRoles.add(role);

        //when
        userService.createUser(user, userRoles);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void willThrowWhenUsernameIsTaken() {
        //given
        User user = new User();
        user.setId("0");
        user.setUsername("admin");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user.setEmail("admin@gmail.com");
        Set<Role> userRoles = new HashSet<>();
        Role role = new Role();
        role.setRoleId("0");
        role.setName("ROLE_ADMIN");
        userRoles.add(role);

        given(userRepository.findByUsername(user.getUsername())).willReturn(user);

        //when
        //then
        assertThat(userService.createUser(user, userRoles))
                .withFailMessage("user {} already exists. Nothing will be done.", user.getUsername());

        verify(userRepository, never()).save(any());
    }

    @Test
    void save() {
        //given
        User user = new User();
        user.setId("0");
        user.setUsername("admin");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user.setEmail("admin@gmail.com");

        //when
        userService.save(user);

        //then
        verify(userRepository).save(user);
    }

    @Test
    void findByUsername() {
        //given
        User user = new User();
        user.setId("0");
        user.setUsername("admin");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user.setEmail("admin@gmail.com");
        userService.save(user);

        //when
        userService.findByUsername(user.getUsername());

        //then
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void findByEmail() {
        //given
        User user = new User();
        user.setId("0");
        user.setUsername("admin");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user.setEmail("admin@gmail.com");
        userService.save(user);

        //when
        userService.findByEmail(user.getEmail());

        //then
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void createPasswordResetTokenForUser() {
        //given
        User user = new User();
        String token = "token";

        //when
        userService.createPasswordResetTokenForUser(user, token);
        //then
        verify(userService, times(1)).createPasswordResetTokenForUser(user, token);

    }

    @Test
    void getPasswordResetToken() {
        //given
        String token = "token";

        //when
        userService.getPasswordResetToken(token);

        //then
        verify(passwordResetTokenRepository).findByToken(token);
    }

    @Test
    void findByIdThrowsException() {
        //given
        User user = new User();
        //when
        //then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.findById(user.getId()));
        assertEquals("user not found", exception.getMessage());
    }

    @Test
    void updateUserPayment() {
        //given
        UserPayment userPayment = new UserPayment();
        User user = new User();
        userPayment.setUser(user);
        userPayment.setDefaultPayment(true);
        user.getUserPaymentList().add(userPayment);

        //when
        userService.updateUserPayment(userPayment, user);

        //then
        verify(userRepository).save(user);
    }

    @Test
    void setUserDefaultPayment() {
    }
}