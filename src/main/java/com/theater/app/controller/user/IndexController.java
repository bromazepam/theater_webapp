package com.theater.app.controller.user;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.User;
import com.theater.app.domain.security.Role;
import com.theater.app.domain.security.UserRole;
import com.theater.app.service.PlayService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.UserService;
import com.theater.app.utility.MailConstructor;
import com.theater.app.utility.SecurityUtility;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
public class IndexController {

    private final PlayService playService;
    private final RepertoireService repertoireService;
    private final UserService userService;
    private final MailConstructor mailConstructor;
    private final JavaMailSender mailSender;

    public IndexController(PlayService playService, RepertoireService repertoireService, UserService userService,
                           MailConstructor mailConstructor, JavaMailSender mailSender) {
        this.playService = playService;
        this.repertoireService = repertoireService;
        this.userService = userService;
        this.mailConstructor = mailConstructor;
        this.mailSender = mailSender;
    }

    @RequestMapping("/")
    public String index(){
        return "user/homescreen";
    }

    @RequestMapping("/login")
    public String login(){
        return "user/login";
    }

    @RequestMapping("/forgottenPass")
    public String forgetPassword(){
        return "user/forgotPassword";
    }

    @RequestMapping("/registration")
    public String registration(){
        return "user/registration";
    }

    @PostMapping("/newUser")
    public String newUserPost(HttpServletRequest request,
                              @ModelAttribute("email") String userEmail,
                              @ModelAttribute("username") String username,
                              Model model) throws Exception {

        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if(userService.findByUsername(username) != null){
            model.addAttribute("usernameExists", true);

            return "user/registration";
        }
        if(userService.findByEmail(userEmail) != null){
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

        String appUrl = "http://" + request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        model.addAttribute("emailSent", true);
        //TODO
//        model.addAttribute("orderList", user.getOrderList());

        return "user/registration";

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

        if(repertoireList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "user/repertoireList";
        }

        return "user/repertoireList";
    }

    @RequestMapping("/repertoireDetail/{id}")
    public String repertoireDetail(@PathVariable String id, Model model){
        Repertoire repertoire = repertoireService.findById(Long.valueOf(id));
        model.addAttribute("repertoire", repertoire);
        return "user/repertoireDetail";
    }
}
