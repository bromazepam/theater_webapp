package com.theater.app.service.impl;

import com.theater.app.domain.*;
import com.theater.app.domain.security.Role;
import com.theater.app.repository.ShoppingCartRepository;
import com.theater.app.service.CartItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * created using diffblue plugin
 */

@ContextConfiguration(classes = {ShoppingCartServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class ShoppingCartServiceImplTest {
    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Test
    public void testUpdateShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(new User());
        shoppingCart.setId("42");
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCart.setGrandTotal(1);

        User user = new User();
        user.setLastName("Doe");
        user.setUserPaymentList(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setId("42");
        user.setOrderList(new ArrayList<Order>());
        user.setPhone("4105551212");
        user.setEnabled(true);
        user.setUserRoles(new HashSet<Role>());
        user.setFirstName("Jane");
        user.setShoppingCart(shoppingCart);

        when(this.shoppingCartRepository.save(any())).thenReturn(shoppingCart);
        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        when(this.cartItemService.findByShoppingCart(any())).thenReturn(cartItemList);
        ShoppingCart shoppingCart3 = new ShoppingCart();
        ShoppingCart actualUpdateShoppingCartResult = this.shoppingCartServiceImpl.updateShoppingCart(shoppingCart3);
        assertSame(shoppingCart3, actualUpdateShoppingCartResult);
        assertSame(cartItemList, actualUpdateShoppingCartResult.getCartItemList());
        assertEquals(0, actualUpdateShoppingCartResult.getGrandTotal());
        then(this.cartItemService).should().findByShoppingCart(any());
        then(this.shoppingCartRepository).should().save(any());
    }

    @Test
    public void testUpdateShoppingCart2() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(new User());
        shoppingCart.setId("42");
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCart.setGrandTotal(1);

        User user = new User();
        user.setLastName("Doe");
        user.setUserPaymentList(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setId("42");
        user.setOrderList(new ArrayList<>());
        user.setPhone("4105551212");
        user.setEnabled(true);
        user.setUserRoles(new HashSet<>());
        user.setFirstName("Jane");
        user.setShoppingCart(shoppingCart);

        Play play = new Play();
        play.setId("42");
        play.setDirector("Director");
        play.setCategory("Category");
        play.setAuthor("JaneDoe");
        play.setTitle("Dr");
        play.setActive(true);
        play.setDescription("The characteristics of someone or something");
        play.setPlayImage("AAAAAAAA".getBytes());

        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        stage.setSeats(new ArrayList<Seat>());

        Repertoire repertoire = new Repertoire();
        repertoire.setStatus(true);
        repertoire.setPlay(play);
        repertoire.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setProjection_datetime(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        repertoire.setStage(stage);
        repertoire.setProjectionTime("Projection Time");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setTimestamp(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        repertoire.setAvailableSeats(0);
        repertoire.setPrice(0);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setProjectionDate(Date.from(atStartOfDayResult2.atZone(ZoneId.systemDefault()).toInstant()));

        CartItem cartItem = new CartItem();
        cartItem.setId("42");
        cartItem.setQty(0);
        cartItem.setRepertoire(repertoire);
        cartItem.setSubtotal(0);
        cartItem.setShoppingCart(shoppingCart);

        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(cartItem);
        when(this.cartItemService.findByShoppingCart(any())).thenReturn(cartItemList);
        ShoppingCart shoppingCart5 = new ShoppingCart();
        ShoppingCart actualUpdateShoppingCartResult = this.shoppingCartServiceImpl.updateShoppingCart(shoppingCart5);
        assertSame(shoppingCart5, actualUpdateShoppingCartResult);
        assertSame(cartItemList, actualUpdateShoppingCartResult.getCartItemList());
        assertEquals(0, actualUpdateShoppingCartResult.getGrandTotal());
        then(this.cartItemService).should().findByShoppingCart(any());
        then(this.shoppingCartRepository).should().save(any());
    }

    @Test
    public void testClearShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(new User());
        shoppingCart.setId("42");
        shoppingCart.setCartItemList(new ArrayList<>());
        shoppingCart.setGrandTotal(1);

        User user = new User();
        user.setLastName("Doe");
        user.setUserPaymentList(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setId("42");
        user.setOrderList(new ArrayList<>());
        user.setPhone("4105551212");
        user.setEnabled(true);
        user.setUserRoles(new HashSet<>());
        user.setFirstName("Jane");
        user.setShoppingCart(shoppingCart);

        when(this.shoppingCartRepository.save(any())).thenReturn(shoppingCart);
        when(this.cartItemService.findByShoppingCart(any())).thenReturn(new ArrayList<>());
        ShoppingCart shoppingCart3 = new ShoppingCart();
        this.shoppingCartServiceImpl.clearShoppingCart(shoppingCart3);
        then(this.cartItemService).should().findByShoppingCart(any());
        then(this.shoppingCartRepository).should().save(any());
        assertEquals(0, shoppingCart3.getGrandTotal());
    }
}

