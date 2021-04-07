package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RepertoireToCartItemTest {

    @Test
    void groupedAssertions() {
        Repertoire repertoire = mock(Repertoire.class);
        CartItem cartItem = mock(CartItem.class);
        RepertoireToCartItem repertoireToCartItem = new RepertoireToCartItem("1", repertoire, cartItem);
        assertAll("RepertoireToCartItem test",
                () -> assertEquals(repertoireToCartItem.getId(), "1"),
                () -> assertEquals(repertoireToCartItem.getRepertoire(), repertoire),
                () -> assertEquals(repertoireToCartItem.getCartItem(), cartItem));
    }

}