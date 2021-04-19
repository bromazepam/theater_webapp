package com.theater.app.service.impl;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.repository.CartItemRepository;
import com.theater.app.repository.RepertoireToCartItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CartItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class CartItemServiceImplTest {
    @MockBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemServiceImpl cartItemServiceImpl;

    @MockBean
    private RepertoireToCartItemRepository repertoireToCartItemRepository;

    @Test
    public void testFindByShoppingCart() {
        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        when(this.cartItemRepository.findByShoppingCart((ShoppingCart) any())).thenReturn(cartItemList);
        List<CartItem> actualFindByShoppingCartResult = this.cartItemServiceImpl.findByShoppingCart(new ShoppingCart());
        assertSame(cartItemList, actualFindByShoppingCartResult);
        assertTrue(actualFindByShoppingCartResult.isEmpty());
        verify(this.cartItemRepository).findByShoppingCart((ShoppingCart) any());
    }

    @Test
    public void testUpdateCartItem() {
        //given
        Repertoire repertoire = new Repertoire();
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        CartItem cartItem = new CartItem();
        cartItem.setId("42");
        cartItem.setQty(1);
        cartItem.setRepertoire(repertoire);
        cartItem.setSubtotal(1);
        cartItem.setShoppingCart(shoppingCart);

        //when
        when(this.cartItemRepository.save(any())).thenReturn(cartItem);
        CartItem actualUpdateCartItemResult = this.cartItemServiceImpl.updateCartItem(cartItem);

        //then
        assertSame(cartItem, actualUpdateCartItemResult);
        assertEquals(0, actualUpdateCartItemResult.getSubtotal());
        verify(this.cartItemRepository).save(any());
    }

    @Test
    public void testRemoveCartItem() {
        doNothing().when(this.repertoireToCartItemRepository).deleteByCartItem((CartItem) any());
        doNothing().when(this.cartItemRepository).delete((CartItem) any());
        this.cartItemServiceImpl.removeCartItem(new CartItem());
        verify(this.cartItemRepository).delete((CartItem) any());
        verify(this.repertoireToCartItemRepository).deleteByCartItem((CartItem) any());
    }

    @Test
    public void testFindById() {
        //given
        Repertoire repertoire = mock(Repertoire.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        CartItem cartItem = new CartItem();
        cartItem.setId("42");
        cartItem.setQty(1);
        cartItem.setRepertoire(repertoire);
        cartItem.setSubtotal(1);
        cartItem.setShoppingCart(shoppingCart);
        Optional<CartItem> ofResult = Optional.<CartItem>of(cartItem);

        //when
        when(this.cartItemRepository.findById(anyString())).thenReturn(ofResult);

        //then
        assertSame(cartItem, this.cartItemServiceImpl.findById("42"));
        verify(this.cartItemRepository).findById(anyString());
    }

    @Test
    public void testSave() {
        //given
        Repertoire repertoire = mock(Repertoire.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        CartItem cartItem = new CartItem();
        cartItem.setId("42");
        cartItem.setQty(1);
        cartItem.setRepertoire(repertoire);
        cartItem.setSubtotal(1);
        cartItem.setShoppingCart(shoppingCart);

        //when
        when(this.cartItemRepository.save(any())).thenReturn(cartItem);

        //then
        assertSame(cartItem, this.cartItemServiceImpl.save(new CartItem()));
        verify(this.cartItemRepository).save(any());
    }

}

