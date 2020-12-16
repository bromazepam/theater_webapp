package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {

    @Test
    void groupedAssertions() {
        Payment payment = new Payment("type", "name", "10", 10, 2020, 10, "joe");

        assertAll("payment test",
                () -> assertEquals(payment.getType(), "type", "payment type failed"),
                () -> assertEquals(payment.getCardName(), "name", "payment cardName failed"),
                () -> assertEquals(payment.getCardNumber(), "10", "payment cardNumber failed"),
                () -> assertEquals(payment.getExpiryMonth(), 10, "payment expiryMonth failed"),
                () -> assertEquals(payment.getExpiryYear(), 2020, "payment expiryYear failed"),
                () -> assertEquals(payment.getCvc(), 10, "payment cvc failed"),
                () -> assertEquals(payment.getHolderName(), "joe", "payment holderName failed"));

    }

}