package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserPaymentTest {

    @Test
    void groupedAssertions() {
        User user = mock(User.class);
        UserPayment userPayment = new UserPayment("1", "type", "name", "number", 10,
                2022, 222, "holderName", true, user);

        assertAll("userPayment test",
                () -> assertEquals(userPayment.getId(), "1", "userPayment id failed"),
                () -> assertEquals(userPayment.getType(), "type", "userPayment type failed"),
                () -> assertEquals(userPayment.getCardName(), "name", "userPayment cardName failed"),
                () -> assertEquals(userPayment.getCardNumber(), "number", "userPayment cardNumber failed"),
                () -> assertEquals(userPayment.getExpiryMonth(), 10, "userPayment expiryMonth failed"),
                () -> assertEquals(userPayment.getExpiryYear(), 2022, "userPayment expiryYear failed"),
                () -> assertEquals(userPayment.getHolderName(), "holderName", "userPayment holderName failed"),
                () -> assertTrue(userPayment.isDefaultPayment(), "userPayment defaultPayment failed"),
                () -> assertEquals(userPayment.getUser(), user, "user failed"));
    }

}