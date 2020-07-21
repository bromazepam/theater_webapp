package com.theater.app.controller.user;

import com.theater.app.domain.*;
import com.theater.app.service.*;
import com.theater.app.utility.MailConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

    Payment payment = new Payment();

    private final UserService userService;
    private final CartItemService cartItemService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final JavaMailSender mailSender;
    private final MailConstructor mailConstructor;
    private final ShoppingCartService shoppingCartService;
    private final UserPaymentService userPaymentService;

    public CheckoutController(UserService userService, CartItemService cartItemService, PaymentService paymentService,
                              OrderService orderService, JavaMailSender mailSender, MailConstructor mailConstructor,
                              ShoppingCartService shoppingCartService, UserPaymentService userPaymentService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.mailSender = mailSender;
        this.mailConstructor = mailConstructor;
        this.shoppingCartService = shoppingCartService;
        this.userPaymentService = userPaymentService;
    }

    @GetMapping("/checkout/{cartId}")
    public String checkout(@PathVariable String cartId, @RequestParam(value = "missingRequiredField", required = false)
            boolean missingrequiredField, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (!Long.valueOf(cartId).equals(user.getShoppingCart().getId())) {
            return "user/badRequestPage";
        }

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        if (cartItemList.size() == 0) {
            model.addAttribute("emptyCart", true);
            return "forward:/shoppingCart";
        }

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getRepertoire().getAvailableSeats() < cartItem.getQty()) {
                model.addAttribute("notEnoughSeats", true);
                return "forward:/shoppingCart";
            }
        }

        List<UserPayment> userPaymentList = user.getUserPaymentList();
        model.addAttribute("userPaymentList", userPaymentList);

        if (userPaymentList.size() == 0) {
            model.addAttribute("emptyPaymentList", true);
        } else {
            model.addAttribute("emptyPaymentList", false);
        }

        for (UserPayment userPayment : userPaymentList) {
            if (userPayment.isDefaultPayment()) {
                paymentService.setByUserPayment(userPayment, payment);
            }
        }

        model.addAttribute("payment", payment);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());

        model.addAttribute("classActivePayment", true);
        if (missingrequiredField) {
            model.addAttribute("missingRequiredField", true);
        }

        return "user/checkout";

    }

    @PostMapping("/checkout")
    public String checkoutPost(@ModelAttribute("payment") Payment payment, Principal principal, Model model) {
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        model.addAttribute("cartItemList", cartItemList);

        User user = userService.findByUsername(principal.getName());
        Order order = orderService.createOrder(shoppingCart, payment, user);

        mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order));
        shoppingCartService.clearShoppingCart(shoppingCart);

        return "user/orderSubmittedPage";

    }

    @RequestMapping("/setPaymentMethod")
    public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(userPaymentId);

        if (!userPayment.getUser().getId().equals(user.getId())) {
            return "user/badRequestPage";
        } else {
            paymentService.setByUserPayment(userPayment, payment);
            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            model.addAttribute("payment", payment);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            List<UserPayment> userPaymentList = user.getUserPaymentList();
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("classActivePayment", true);
            model.addAttribute("emptyPaymentList", false);

            return "user/checkout";
        }
    }
}
