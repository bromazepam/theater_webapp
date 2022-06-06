package com.theater.app.service.impl;

import com.theater.app.domain.Payment;
import com.theater.app.domain.UserPayment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

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