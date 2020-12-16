package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CartItemTest {

    @Test
    void groupedAssertions() {
        Repertoire repertoire = mock(Repertoire.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        CartItem cartItem = new CartItem("1", 10, 100, repertoire, shoppingCart);

        assertAll("cartitem test",
                () -> assertEquals(cartItem.getId(), "1", "cartitem id failed"),
                () -> assertEquals(cartItem.getQty(), 10, "cartitem quantity failed"),
                () -> assertEquals(cartItem.getSubtotal(), 100, "subtotal failed"),
                () -> assertEquals(cartItem.getRepertoire(), repertoire, "repertoire failed"),
                () -> assertEquals(cartItem.getShoppingCart(), shoppingCart, "shoppingCart failed"));
    }

}