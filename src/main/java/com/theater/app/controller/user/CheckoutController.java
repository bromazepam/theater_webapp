package com.theater.app.controller.user;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.Payment;
import com.theater.app.domain.User;
import com.theater.app.domain.UserPayment;
import com.theater.app.service.CartItemService;
import com.theater.app.service.PaymentService;
import com.theater.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CheckoutController {

    Payment payment = new Payment();

    private final UserService userService;
    private final CartItemService cartItemService;
    private final PaymentService paymentService;

    public CheckoutController(UserService userService, CartItemService cartItemService, PaymentService paymentService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.paymentService = paymentService;
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("id") Long cartId, @RequestParam(value = "missingRequiredField", required = false)
                           boolean missingrequiredField, Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());

        if(!cartId.equals(user.getShoppingCart().getId())){
            return "user/badRequestPage";
        }

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        if(cartItemList.size() == 0){
            model.addAttribute("emptyCart", true);
            return "forward:/shoppingCart";
        }

        for(CartItem cartItem: cartItemList){
            if(cartItem.getRepertoire().getAvailableSeats() < cartItem.getQty()){
                model.addAttribute("notEnoughSeats", true);
                return "forward:/shoppingCart";
            }
        }

        List<UserPayment> userPaymentList = user.getUserPaymentList();
        model.addAttribute("userPaymentList", userPaymentList);

        if(userPaymentList.size()==0){
            model.addAttribute("emptyPaymentList", true);
        } else {
            model.addAttribute("emptyPaymentList", false);
        }

        for(UserPayment userPayment: userPaymentList){
            if(userPayment.isDefaultPayment()){
                paymentService.setByUserPayment(userPayment, payment);
            }
        }

        model.addAttribute("payment", payment);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());

        model.addAttribute("classActivePayment", true);
        if(missingrequiredField){
            model.addAttribute("missingRequiredField", true);
        }

        return "checkout";

    }
}
