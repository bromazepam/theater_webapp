package com.theater.app.controller.user;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.domain.User;
import com.theater.app.service.CartItemService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.ShoppingCartService;
import com.theater.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingCartController {

    private final UserService userService;
    private final RepertoireService repertoireService;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(UserService userService, RepertoireService repertoireService,
                                  CartItemService cartItemService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.repertoireService = repertoireService;
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "user/shoppingCart";
    }


    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("repertoire")Repertoire repertoire,
                          @ModelAttribute("qty") String qty, Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        repertoire = repertoireService.findById(repertoire.getId());

        if(Integer.parseInt(qty) > repertoire.getAvailableSeats()){
            model.addAttribute("notEnoughSeats", true);
            return "forward:/repertoireDetail/"+repertoire.getId();
        }

        CartItem cartItem = cartItemService.addRepertoireToCartItem(repertoire, user, Integer.parseInt(qty));
        model.addAttribute("addReservationSuccess", true);

        return "forward:/repertoireDetail/" + repertoire.getId();
    }
}
