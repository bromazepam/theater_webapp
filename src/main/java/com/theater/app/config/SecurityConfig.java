package com.theater.app.config;

import com.theater.app.service.impl.UserSecurityService;
import com.theater.app.utility.SecurityUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserSecurityService userSecurityService;
	private final AuthenticationSuccessHandler successHandler;

	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/fonts/**",
			"/newUser",
			"/forgetPassword",
			"/login",
			"/forgottenPass",
			"/registration",
			"/homescreen",
			"/faq",
			"/about",
			"/contact",
			"/gallery",
			"/plays",
			"/searchByCategory",
			"/repertoireList",
			"/repertoireDetail/**",
			"/"
	};

	private static final String[] PRIVATE_MATCHERS = {
			"/reports",
			"/adminHome",
			"/add",
			"/playList",
			"/addRepertoire",
			"/repertoire",
			"/addStage",
			"/stageList"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(PRIVATE_MATCHERS)
					.access("hasRole('ROLE_ADMIN')")
				.antMatchers(PUBLIC_MATCHERS)
					.permitAll().anyRequest().authenticated();

		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/login?error")
			.defaultSuccessUrl("/")
			.loginPage("/login")
				.successHandler(successHandler)
				.permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

}
