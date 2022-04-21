package com.theater.app.repository;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.ShoppingCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteById("1");
        shoppingCartRepository.deleteById("1");
    }

    @Test
    void findByShoppingCart() {
        //given
        ShoppingCart cart = ShoppingCart.builder()
                .id("1").build();
        shoppingCartRepository.save(cart);
        CartItem item = CartItem.builder()
                .shoppingCart(cart)
                .id("1")
                .build();
        cartItemRepository.save(item);
        //when
        List<CartItem> expected = cartItemRepository.findByShoppingCart(cart);
        //then
        assertThat(expected).hasSize(1);
        assertThat(expected.get(0).getShoppingCart().getId()).isEqualTo("1");
    }
}