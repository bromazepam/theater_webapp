package com.theater.app.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserTest {

    @Test
    void groupedAssertions() {
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        ArrayList arrayList = mock(ArrayList.class);
        Set set = mock(Set.class);
        User user = new User("1", "username", "password", "firstName", "lastName",
                "email", "phone", true, shoppingCart, arrayList, arrayList, set);

        assertAll("user test",
                () -> assertEquals(user.getId(), "1"),
                () -> assertEquals(user.getUsername(), "username"),
                () -> assertEquals(user.getPassword(), "password"),
                () -> assertEquals(user.getFirstName(), "firstName"),
                () -> assertEquals(user.getLastName(), "lastName"),
                () -> assertEquals(user.getEmail(), "email"),
                () -> assertEquals(user.getPhone(), "phone"),
                () -> assertTrue(user.isEnabled(), "user enabled failed"),
                () -> assertEquals(user.getShoppingCart(), shoppingCart),
                () -> assertEquals(user.getUserPaymentList(), arrayList),
                () -> assertEquals(user.getOrderList(), arrayList),
                () -> assertEquals(user.getUserRoles(), set));
    }

}