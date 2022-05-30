package com.theater.app.utility;

import com.theater.app.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MailConstructor.class, TemplateEngine.class})
@ExtendWith(SpringExtension.class)
public class MailConstructorTest {
    @MockBean
    private Environment environment;

    @Autowired
    private MailConstructor mailConstructor;

    @MockBean
    private TemplateEngine templateEngine;

    @Test
    public void testConstructResetTokenEmail2() {
        when(this.environment.getProperty(anyString())).thenReturn("foo");
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("foo");
        SimpleMailMessage actualConstructResetTokenEmailResult = this.mailConstructor
                .constructResetTokenEmail("Context Path", "ABC123", user, "iloveyou");
        assertEquals("SimpleMailMessage: from=foo; replyTo=null; to=foo; cc=; bcc=; sentDate=null; subject=Pozoriste - Novi"
                + " Korisnik; text=Context Path/newUser?token=ABC123\n"
                + "Molim Vas potvrdite Vas email klikom na link , a zatim izmenite Vase informacije. Vasa sifra za pristup"
                + " nalogu je: \n" + "iloveyou", actualConstructResetTokenEmailResult.toString());
        assertEquals(1, actualConstructResetTokenEmailResult.getTo().length);
        assertEquals("Context Path/newUser?token=ABC123\n"
                + "Molim Vas potvrdite Vas email klikom na link , a zatim izmenite Vase informacije. Vasa sifra za pristup"
                + " nalogu je: \n" + "iloveyou", actualConstructResetTokenEmailResult.getText());
        assertEquals("Pozoriste - Novi Korisnik", actualConstructResetTokenEmailResult.getSubject());
        assertEquals("foo", actualConstructResetTokenEmailResult.getFrom());
        then(this.environment).should().getProperty(anyString());
        then(user).should().getEmail();
    }
}

