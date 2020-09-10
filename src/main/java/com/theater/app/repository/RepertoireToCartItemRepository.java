package com.theater.app.repository;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.RepertoireToCartItem;
import org.springframework.data.repository.CrudRepository;

public interface RepertoireToCartItemRepository extends CrudRepository<RepertoireToCartItem, String> {
    void deleteByCartItem(CartItem cartItem);
}
