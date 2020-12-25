package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
class OrderTest {

    @Test
    void groupedAssertions() {
        Payment payment = mock(Payment.class);
        ArrayList arrayList = mock(ArrayList.class);
        Date date = mock(Date.class);
        Order order = new Order("1", date, "ok", 100, arrayList, payment, 100);

        assertAll("order test",
                () -> assertEquals(order.getId(), "1", "order id failed"),
                () -> assertEquals(order.getOrderDate(), date, "order date failed"),
                () -> assertEquals(order.getOrderStatus(), "ok", "order status failed"),
                () -> assertEquals(order.getOrderTotal(), 100, "order total failed"),
                () -> assertEquals(order.getCartItemList(), arrayList, "cartItem list failed"),
                () -> assertEquals(order.getPayment(), payment, "order payment failed"));
    }

}