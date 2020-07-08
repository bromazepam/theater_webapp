package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.User;
import com.theater.app.domain.security.PasswordResetToken;
import com.theater.app.domain.security.Role;
import com.theater.app.domain.security.UserRole;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.UserService;
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
import java.util.*;

@Controller
public class IndexController {

    private final PlayService playService;
    private final RepertoireService repertoireService;
    private final UserService userService;
    private final MailConstructor mailConstructor;
    private final JavaMailSender mailSender;
    private final UserSecurityService userSecurityService;

    public IndexController(PlayService playService, RepertoireService repertoireService, UserService userService,
                           MailConstructor mailConstructor, JavaMailSender mailSender,
                           UserSecurityService userSecurityService) {
        this.playService = playService;
        this.repertoireService = repertoireService;
        this.userService = userService;
        this.mailConstructor = mailConstructor;
        this.mailSender = mailSender;
        this.userSecurityService = userSecurityService;
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
        //TODO
//        model.addAttribute("orderList", user.getOrderList());

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
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model){
        User user = userService.findByEmail(email);

        if(user == null){
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
        if(currentUser == null){
            throw new Exception("User not found");
        }

        if (userService.findByEmail(user.getEmail()) != null){
            if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()){
                model.addAttribute("emailExists", true);
                return "user/myProfile";
            }
        }

        if(userService.findByUsername(user.getUsername()) != null){
            if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()){
                model.addAttribute("usernameExists", true);
                return "user/myProfile";
            }
        }

        if(newPassword!=null && !newPassword.isEmpty() && !newPassword.equals("")){
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
        //TODO kada se odradi rezervacija
//        model.addAttribute("orderList", user.getOrderList());

        return "user/myProfile";
    }

    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        //TODO kada se odrade rezervacije
//        model.addAttribute("userReservationList", user.getUserReservationsList());
        model.addAttribute("classActiveEdit", true);

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
    public String repertoire(Model model) {

        List<Repertoire> repertoireList = repertoireService.findAll();
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
        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Repertoire repertoire = repertoireService.findById(Long.valueOf(id));
        model.addAttribute("repertoire", repertoire);

        List<Integer> qtyList = Arrays.asList(1,2,3,4,5);

        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);
        return "user/repertoireDetail";
    }
}
