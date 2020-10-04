package com.theater.app.utility;

import com.theater.app.domain.Order;
import com.theater.app.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;

@RequiredArgsConstructor
@Component
public class MailConstructor {

    private final Environment env;
    private final TemplateEngine templateEngine;

    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, User user, String password) {
        String url = contextPath + "/newUser?token=" + token;
        String message = "\nMolim Vas potvrdite Vas email klikom na link , a zatim izmenite Vase informacije." +
                " Vasa sifra za pristup nalogu je: \n" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Pozoriste - Novi Korisnik");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));
        return email;

    }

    public MimeMessagePreparator constructOrderConfirmationEmail(User user, Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("user", user);
        context.setVariable("cartItemList", order.getCartItemList());
        String text = templateEngine.process("user/orderConfirmationEmailTemplate", context);

        return mimeMessage -> {
            MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
            email.setTo(user.getEmail());
            email.setSubject("order confirmation - " + order.getId());
            email.setText(text, true);
            email.setFrom(new InternetAddress("beermezv@gmail.com"));
        };
    }
}
