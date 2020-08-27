package com.theater.app.controller.user;

import com.theater.app.domain.*;
import com.theater.app.domain.security.PasswordResetToken;
import com.theater.app.domain.security.Role;
import com.theater.app.domain.security.UserRole;
import com.theater.app.service.*;
import com.theater.app.service.impl.UserSecurityService;
import com.theater.app.utility.MailConstructor;
import com.theater.app.utility.SecurityUtility;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndexController {

    private final PlayService playService;
    private final RepertoireService repertoireService;
    private final UserService userService;
    private final MailConstructor mailConstructor;
    private final JavaMailSender mailSender;
    private final UserSecurityService userSecurityService;
    private final UserPaymentService userPaymentService;
    private final OrderService orderService;
    private final CartItemService cartItemService;

    public IndexController(PlayService playService, RepertoireService repertoireService,
                           UserService userService, MailConstructor mailConstructor,
                           JavaMailSender mailSender, UserSecurityService userSecurityService,
                           UserPaymentService userPaymentService, OrderService orderService,
                           CartItemService cartItemService) {
        this.playService = playService;
        this.repertoireService = repertoireService;
        this.userService = userService;
        this.mailConstructor = mailConstructor;
        this.mailSender = mailSender;
        this.userSecurityService = userSecurityService;
        this.userPaymentService = userPaymentService;
        this.orderService = orderService;
        this.cartItemService = cartItemService;
    }

    @RequestMapping("/")
    public String index() {
        return "user/homescreen";
    }

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/forgottenPass")
    public String forgottenPassword() {
        return "user/forgotPassword";
    }

    @RequestMapping("/registration")
    public String registration() {
        return "user/registration";
    }

    @PostMapping("/newUser")
    public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
                              @ModelAttribute("username") String username, Model model) throws Exception {

        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);

            return "user/registration";
        }
        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);

            return "user/registration";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        model.addAttribute("emailSent", true);

        model.addAttribute("orderList", user.getOrderList());

        return "user/registration";

    }

    @GetMapping("/newUser")
    public String newUser(@RequestParam("token") String token, Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if (passToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);
        model.addAttribute("classActiveEdit", true);

        return "user/myProfile";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {
        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return "user/forgotPassword";
        }
        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
        mailSender.send(newEmail);
        model.addAttribute("forgetPasswordEmailSent", true);
        return "user/forgotPassword";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute("user") User user,
                                 @ModelAttribute("newPassword") String newPassword, Model model) throws Exception {
        User currentUser = userService.findById(user.getId());
        if (currentUser == null) {
            throw new Exception("User not found");
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            if (!userService.findByEmail(user.getEmail()).getId().equals(currentUser.getId())) {
                model.addAttribute("emailExists", true);
                return "user/myProfile";
            }
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            if (!userService.findByUsername(user.getUsername()).getId().equals(currentUser.getId())) {
                model.addAttribute("usernameExists", true);
                return "user/myProfile";
            }
        }

        if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentUser.getPassword();
            if (passwordEncoder.matches(user.getPassword(), dbPassword)) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                model.addAttribute("incorrectPassword", true);
                return "user/myProfile";
            }
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.save(currentUser);

        model.addAttribute("updateSuccess", true);
        model.addAttribute("user", currentUser);
        model.addAttribute("classActiveEdit", true);

        UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("orderList", user.getOrderList());

        return "user/myProfile";
    }

    @RequestMapping("/plays")
    public String plays(Model model) {

        List<Play> playList = playService.findAll();
        model.addAttribute("playList", playList);
        model.addAttribute("activeAll", true);

        return "user/plays";
    }

    @RequestMapping("/repertoireList")
    public String repertoire(Model model) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date date2 = dateFormat.parse(dateFormat.format(date));

        List<Repertoire> repertoireList = repertoireService.findByPresentOrFutureDate(date2);
        repertoireList.sort(Comparator.comparing(Repertoire::getProjectionDate));
        model.addAttribute("repertoireList", repertoireList);
        model.addAttribute("activeAll", true);

        if (repertoireList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "user/repertoireList";
        }

        return "user/repertoireList";
    }

    @RequestMapping("/repertoireDetail/{id}")
    public String repertoireDetail(@PathVariable String id, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Repertoire repertoire = repertoireService.findById(Long.valueOf(id));
        model.addAttribute("repertoire", repertoire);

        List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5);

        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);
        return "user/repertoireDetail";
    }

    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("orderList", user.getOrderList());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveEdit", true);

        return "user/myProfile";
    }

    @RequestMapping("/orders")
    public String orders(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("classActiveOrders", true);
        model.addAttribute("orderList", user.getOrderList());

        return "user/orders";
    }

    @RequestMapping("/orderDetail")
    public String orderDetail(@RequestParam("id") Long orderId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        Order order = orderService.findById(orderId);

        if (!order.getUser().getId().equals(user.getId())) {
            return "user/badRequestPage";
        } else {
            List<CartItem> cartItemList = cartItemService.findByOrder(order);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("user", user);
            model.addAttribute("order", order);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("orderList", user.getOrderList());

            model.addAttribute("classActiveOrders", true);
            model.addAttribute("displayOrderDetail", true);

            return "user/orders";

        }
    }

    @RequestMapping("/listOfCreditCards")
    public String listOfCreditCards(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("orderList", user.getOrderList());
        model.addAttribute("classActiveBilling", true);

        return "user/myProfile";
    }

    @RequestMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);

        UserPayment userPayment = new UserPayment();
        model.addAttribute("userPayment", userPayment);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("orderList", user.getOrderList());

        return "user/myProfile";
    }

    @PostMapping("/addNewCreditCard")
    public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
                                   Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        userService.updateUserPayment(userPayment, user);

        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("orderList", user.getOrderList());

        return "user/myProfile";
    }

    @RequestMapping("/updateCreditCard")
    public String updateCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(creditCardId);

        if (!user.getId().equals(userPayment.getUser().getId())) {
            return "user/badRequestPage";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("userPayment", userPayment);

            model.addAttribute("addNewCreditCard", true);
            model.addAttribute("classActiveBilling", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("orderList", user.getOrderList());

            return "user/myProfile";
        }
    }

    @PostMapping("/setDefaultPayment")
    public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId,
                                    Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultPayment(defaultPaymentId, user);

        model.addAttribute("user", user);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("orderList", user.getOrderList());

        return "user/myProfile";
    }

    @RequestMapping("/removeCreditCard")
    public String removeCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(creditCardId);

        if (!user.getId().equals(userPayment.getUser().getId())) {
            return "user/badRequestPage";
        } else {
            model.addAttribute("user", user);
            userPaymentService.removeById(creditCardId);

            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("classActiveBilling", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("orderList", user.getOrderList());

            return "user/myProfile";
        }
    }

    @RequestMapping("/faq")
    public String faq() {
        return "user/faq";
    }

    @RequestMapping("/about")
    public String about() {
        return "user/about";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "user/contact";
    }
}
