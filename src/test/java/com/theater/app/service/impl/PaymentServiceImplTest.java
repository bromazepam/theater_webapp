package com.theater.app.service.impl;

import com.theater.app.domain.Payment;
import com.theater.app.domain.UserPayment;
import com.theater.app.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentService paymentService;

    @Test
    void setByUserPayment() {
        //given
        UserPayment userPayment = mock(UserPayment.class);
        Payment payment = mock(Payment.class);

        //when
        payment.setType(userPayment.getType());
        payment.setHolderName(userPayment.getHolderName());
        payment.setCardName(userPayment.getCardName());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());


        //then
        assertNotNull(payment);

    }
}