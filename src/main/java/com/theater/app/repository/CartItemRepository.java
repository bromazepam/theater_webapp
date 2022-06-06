package com.theater.app.repository;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, String> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

}
