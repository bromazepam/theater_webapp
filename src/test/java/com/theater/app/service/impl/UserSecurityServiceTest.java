package com.theater.app.service.impl;

import com.theater.app.domain.User;
import com.theater.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceTest {

    @InjectMocks
    UserSecurityService userSecurityService;

    @Mock
    UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        //given
        given(userRepository.findByUsername(anyString())).willReturn(new User());

        //when
        User user = userRepository.findByUsername(anyString());

        //then
        then(userRepository).should().findByUsername(anyString());
        assertThat(user).isNotNull();
    }

    @Test
    void loadUserByUsernameNotFound() {
        User user = userRepository.findByUsername("2");

        assertThat(user).isNull();
    }
}