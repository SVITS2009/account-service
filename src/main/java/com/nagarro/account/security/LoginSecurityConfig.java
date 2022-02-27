package com.nagarro.account.security;

import com.nagarro.account.config.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Implements LoginSecurityConfig class to secure the rest API.
 * Override Configure(AuthenticationManagerBuilder) method to create 2 users as User1(username = admin, password = admin)
 * and User2 (username = user, password = user)
 *
 * Override configure(HttpSecurity http) method to maintain single sign on per user.
 *  performing role based API execution which can restrict other user to access the secure resources
 *  Using BCryptPasswordEncoder to encode password
 *  Using form based authentication to secure resources
 *
 *  If user tries to access restricted resource, server returns 403 forbidden as http status code,
 *  to avoid that, implemented CustomAccessDeniedHandler to return 401 unauthorized with customize
 *  message and details when uer tries to access restricted API.
 */

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password(this.passwordEncoder().encode("admin"))
				.roles("User1");

		auth.inMemoryAuthentication()
				.withUser("user")
				.password(this.passwordEncoder().encode("user"))
				.roles("User2");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

		http.sessionManagement().maximumSessions(1);

		http.csrf().disable().authorizeRequests()
				.regexMatchers(HttpMethod.GET, "/account/search/[0-9]+").access("hasRole('User2') or hasRole('User1')")
				.antMatchers(HttpMethod.GET, "/account/search/{accountId}").access("hasRole('User1')")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin();

		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
}