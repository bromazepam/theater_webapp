package com.theater.app.controller.user;

import com.sun.security.auth.UserPrincipal;
import com.theater.app.domain.*;
import com.theater.app.domain.security.Role;
import com.theater.app.service.*;
import com.theater.app.service.impl.UserSecurityService;
import com.theater.app.utility.MailConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {IndexController.class})
@ExtendWith(SpringExtension.class)
public class IndexControllerTest {
    @Autowired
    private IndexController indexController;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private MailConstructor mailConstructor;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PlayService playService;

    @MockBean
    private RepertoireService repertoireService;

    @MockBean
    private UserPaymentService userPaymentService;

    @MockBean
    private UserSecurityService userSecurityService;

    @MockBean
    private UserService userService;

    @Test
    public void testAbout() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/about");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/about"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/about"));
    }

    @Test
    public void testAddNewCreditCard() throws Exception {
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
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/addNewCreditCard");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("addNewCreditCard", "classActiveBilling", "orderList", "user", "userPayment",
                                "userPaymentList"))
                .andExpect(MockMvcResultMatchers.view().name("user/myProfile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/myProfile"));
    }

    @Test
    public void testContact() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/contact");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/contact"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/contact"));
    }

    @Test
    public void testError() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/error");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/badRequestPage"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/badRequestPage"));
    }

    @Test
    public void testFaq() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/faq");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/faq"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/faq"));
    }

    @Test
    public void testForgottenPassword() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forgottenPass");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/forgotPassword"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/forgotPassword"));
    }

    @Test
    public void testGallery() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gallery");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/gallery"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/gallery"));
    }

    @Test
    public void testIndex() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/homescreen"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/homescreen"));
    }

    @Test
    public void testListOfCreditCards() throws Exception {
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
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/listOfCreditCards");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("classActiveBilling", "orderList", "user", "userPaymentList"))
                .andExpect(MockMvcResultMatchers.view().name("user/myProfile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/myProfile"));
    }

    @Test
    public void testLogin() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/login");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/login"));
    }

    @Test
    public void testMyProfile() throws Exception {
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
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/myProfile");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("classActiveEdit", "listOfCreditCards", "orderList", "user", "userPaymentList"))
                .andExpect(MockMvcResultMatchers.view().name("user/myProfile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/myProfile"));
    }

    @Test
    public void testNewUserPost() throws Exception {
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/newUser");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email", "username", "usernameExists"))
                .andExpect(MockMvcResultMatchers.view().name("user/registration"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/registration"));
    }

    @Test
    public void testOrderDetail() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orderDetail").param("orderId", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testOrders() throws Exception {
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
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders");
        getResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("classActiveOrders", "orderList", "user"))
                .andExpect(MockMvcResultMatchers.view().name("user/orders"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/orders"));
    }

    @Test
    public void testPlays() throws Exception {
        when(this.playService.findAll()).thenReturn(new ArrayList<Play>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/plays");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("activeAll", "playList"))
                .andExpect(MockMvcResultMatchers.view().name("user/plays"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/plays"));
    }

    @Test
    public void testRegistration() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/registration");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/registration"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/registration"));
    }

    @Test
    public void testRepertoire() throws Exception {
        when(this.repertoireService.findByPresentOrFutureDate((java.util.Date) any()))
                .thenReturn(new ArrayList<Repertoire>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/repertoireList");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("activeAll", "emptyList", "repertoireList"))
                .andExpect(MockMvcResultMatchers.view().name("user/repertoireList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/repertoireList"));
    }

    @Test
    public void testRepertoire2() throws Exception {
        Play play = new Play();
        play.setId("42");
        play.setDirector("?");
        play.setCategory("?");
        play.setAuthor("JaneDoe");
        play.setTitle("Dr");
        play.setActive(true);
        play.setDescription("The characteristics of someone or something");
        play.setPlayImage("AAAAAAAA".getBytes());

        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("?");
        stage.setSeats(new ArrayList<Seat>());

        Repertoire repertoire = new Repertoire();
        repertoire.setStatus(true);
        repertoire.setPlay(play);
        repertoire.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setProjection_datetime(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        repertoire.setStage(stage);
        repertoire.setProjectionTime("?");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setTimestamp(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        repertoire.setAvailableSeats(0);
        repertoire.setPrice(0);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setProjectionDate(Date.from(atStartOfDayResult2.atZone(ZoneId.systemDefault()).toInstant()));

        ArrayList<Repertoire> repertoireList = new ArrayList<Repertoire>();
        repertoireList.add(repertoire);
        when(this.repertoireService.findByPresentOrFutureDate((Date) any())).thenReturn(repertoireList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/repertoireList");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("activeAll", "repertoireList"))
                .andExpect(MockMvcResultMatchers.view().name("user/repertoireList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/repertoireList"));
    }

    @Test
    public void testRepertoire3() throws Exception {
        when(this.repertoireService.findByPresentOrFutureDate((java.util.Date) any()))
                .thenReturn(new ArrayList<Repertoire>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/repertoireList");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("activeAll", "emptyList", "repertoireList"))
                .andExpect(MockMvcResultMatchers.view().name("user/repertoireList"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/repertoireList"));
    }

    @Test
    public void testRepertoireDetail() throws Exception {
        Play play = new Play();
        play.setId("42");
        play.setDirector("Director");
        play.setCategory("Category");
        play.setAuthor("JaneDoe");
        play.setTitle("Dr");
        play.setActive(true);
        play.setDescription("The characteristics of someone or something");
        play.setPlayImage("AAAAAAAA".getBytes());

        Stage stage = new Stage();
        stage.setCapacity(3);
        stage.setId("42");
        stage.setName("Name");
        stage.setSeats(new ArrayList<Seat>());

        Repertoire repertoire = new Repertoire();
        repertoire.setStatus(true);
        repertoire.setPlay(play);
        repertoire.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setProjection_datetime(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        repertoire.setStage(stage);
        repertoire.setProjectionTime("Projection Time");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setTimestamp(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        repertoire.setAvailableSeats(1);
        repertoire.setPrice(1);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        repertoire.setProjectionDate(Date.from(atStartOfDayResult2.atZone(ZoneId.systemDefault()).toInstant()));
        when(this.repertoireService.findById(anyString())).thenReturn(repertoire);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/repertoireDetail/{id}", "value");
        MockMvcBuilders.standaloneSetup(this.indexController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("qty", "qtyList", "repertoire"))
                .andExpect(MockMvcResultMatchers.view().name("user/repertoireDetail"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/repertoireDetail"));
    }
}

