package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ShoppingCartTest {

    @Test
    void groupedAssertions() {
        User user = mock(User.class);
        ArrayList arrayList = mock(ArrayList.class);

        ShoppingCart shoppingCart = new ShoppingCart("1", 100, arrayList, user);

        assertAll("shoppingCart test",
                () -> assertEquals(shoppingCart.getId(), "1"),
                () -> assertEquals(shoppingCart.getGrandTotal(), 100),
                () -> assertEquals(shoppingCart.getCartItemList(), arrayList),
                () -> assertEquals(shoppingCart.getUser(), user));
    }

}