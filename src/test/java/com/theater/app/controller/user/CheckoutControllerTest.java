package com.theater.app.controller.user;

import com.sun.security.auth.UserPrincipal;
import com.theater.app.domain.*;
import com.theater.app.domain.security.Role;
import com.theater.app.service.*;
import com.theater.app.utility.MailConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CheckoutController.class})
@ExtendWith(SpringExtension.class)
public class CheckoutControllerTest {
    @MockBean
    private CartItemService cartItemService;

    @Autowired
    private CheckoutController checkoutController;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private MailConstructor mailConstructor;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserPaymentService userPaymentService;

    @MockBean
    private UserService userService;

    @Test
    public void testCheckout() throws Exception {
        User user = new User();
        user.setLastName("Doe");
        user.setUserPaymentList(new ArrayList<UserPayment>());
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setId("42");
        user.setOrderList(new ArrayList<Order>());
        user.setPhone("4105551212");
        user.setEnabled(true);
        user.setUserRoles(new HashSet<Role>());
        user.setFirstName("Jane");
        user.setShoppingCart(new ShoppingCart());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setId("42");
        shoppingCart.setCartItemList(new ArrayList<CartItem>());
        shoppingCart.setGrandTotal(1);

        when(this.userService.findByUsername(anyString())).thenReturn(user);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/checkout/{cartId}", "value");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.checkoutController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/badRequestPage"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/badRequestPage"));
    }
}

