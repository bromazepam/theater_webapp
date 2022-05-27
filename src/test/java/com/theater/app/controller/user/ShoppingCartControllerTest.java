package com.theater.app.controller.user;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.User;
import com.theater.app.repository.ShoppingCartRepository;
import com.theater.app.service.CartItemService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.ShoppingCartService;
import com.theater.app.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    @InjectMocks
    ShoppingCartController shoppingCartController;

    @Mock
    UserService userService;
    @Mock
    RepertoireService repertoireService;
    @Mock
    CartItemService cartItemService;
    @Mock
    ShoppingCartService shoppingCartService;
    @Mock
    ShoppingCartRepository shoppingCartRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @AfterEach
    void tearDown() {
        reset(userService, repertoireService, cartItemService, shoppingCartService, shoppingCartRepository);
    }

    @Test
    void shoppingCart() {
    }

    //todo
//    @Disabled
//    @Test
//    void addItem() throws Exception {
//        User user = new User();
//        Repertoire repertoire = new Repertoire();
//        int qty = 100;
//        CartItem cartItem = new CartItem();
//
//        given(cartItemService.addRepertoireToCartItem(repertoire, user, qty)).willReturn(new CartItem());
//
//        mockMvc.perform(post("/addItem"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("forward:/repertoireDetail/"));
//
//        then(cartItemService).should().addRepertoireToCartItem(any(), any(), any());
//    }

    @Test
    void updateShoppingCart() throws Exception {


    }

    @Test
    void removeItem() throws Exception {
        doNothing().when(cartItemService).removeCartItem(any());

        this.mockMvc.perform(get("/shoppingCart/removeItem/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/shoppingCart"));
    }
}