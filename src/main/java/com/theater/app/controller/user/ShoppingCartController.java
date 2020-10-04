package com.theater.app.controller.user;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.domain.User;
import com.theater.app.repository.ShoppingCartRepository;
import com.theater.app.service.CartItemService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.ShoppingCartService;
import com.theater.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ShoppingCartController {

    private final UserService userService;
    private final RepertoireService repertoireService;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        if(shoppingCart == null){
            ShoppingCart shoppingCart1 = new ShoppingCart();
            shoppingCart1.setUser(user);
            shoppingCartRepository.save(shoppingCart1);
            user.setShoppingCart(shoppingCart1);
            userService.save(user);
        }
        ShoppingCart shoppingCart2 = user.getShoppingCart();
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart2);

        shoppingCartService.updateShoppingCart(shoppingCart2);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart2);

        return "user/shoppingCart";
    }


    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("repertoire") Repertoire repertoire,
                          @ModelAttribute("qty") String qty, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        repertoire = repertoireService.findById(repertoire.getId());

        if (Integer.parseInt(qty) > repertoire.getAvailableSeats()) {
            model.addAttribute("notEnoughSeats", true);
            return "forward:/repertoireDetail/" + repertoire.getId();
        }

        cartItemService.addRepertoireToCartItem(repertoire, user, Integer.parseInt(qty));
        model.addAttribute("addReservationSuccess", true);

        return "forward:/repertoireDetail/" + repertoire.getId();
    }

    @RequestMapping("/updateCartItem")
    public String updateShoppingCart(@ModelAttribute("id") String cartItemId,
                                     @ModelAttribute("qty") int qty) {
        CartItem cartItem = cartItemService.findById(cartItemId);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);

        return "forward:/shoppingCart";
    }

    @Transactional
    @RequestMapping("shoppingCart/removeItem/{id}")
    public String removeItem(@PathVariable String id) {
        cartItemService.removeCartItem(cartItemService.findById(id));
        return "forward:/shoppingCart";
    }
}
