package com.theater.app.service.impl;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.repository.ShoppingCartRepository;
import com.theater.app.service.CartItemService;
import com.theater.app.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, CartItemService cartItemService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
    }

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        int cartTotal = 0;
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getRepertoire().getAvailableSeats() > 0) {
                cartItemService.updateCartItem(cartItem);
                cartTotal += cartItem.getSubtotal();
            }
        }
        shoppingCart.setGrandTotal(cartTotal);
        shoppingCart.setCartItemList(cartItemList);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    @Override
    public void clearShoppingCart(ShoppingCart shoppingCart) {
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            cartItem.setShoppingCart(null);
            cartItemService.save(cartItem);
        }
        shoppingCart.setGrandTotal(0);
        shoppingCartRepository.save(shoppingCart);
    }
}
