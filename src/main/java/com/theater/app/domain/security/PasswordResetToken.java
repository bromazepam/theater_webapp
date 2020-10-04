package com.theater.app.domain.security;

import com.theater.app.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@ToString
@Document
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	private String id;
	private String token;
	private User user;
	private Date expiryDate;

	public PasswordResetToken(){}

	public PasswordResetToken(final String token, final User user) {
		super ();
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate();
	}

	private Date calculateExpiryDate() {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
		return new Date(cal.getTime().getTime());
	}

	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate();
	}

	public static int getExpiration() {
		return EXPIRATION;
	}


}
