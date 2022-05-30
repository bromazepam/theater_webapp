package com.theater.app.service.impl;

import com.theater.app.domain.UserPayment;
import com.theater.app.repository.UserPaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserPaymentServiceTest {

    @InjectMocks
    UserPaymentServiceImpl userPaymentService;

    @Mock
    UserPaymentRepository userPaymentRepository;

    @Test
    void findById() {
        //given
        given(userPaymentRepository.findById(anyString())).willReturn(java.util.Optional.of(new UserPayment()));

        //when
        Optional<UserPayment> userPayment = userPaymentRepository.findById(anyString());

        //then
        then(userPaymentRepository).should().findById(anyString());
        assertThat(userPayment).isNotNull();
    }

    @Test
    void removeById() {
        //given

        //when
        userPaymentService.removeById(anyString());

        //then
        then(userPaymentRepository).should().deleteById(any());
    }
}