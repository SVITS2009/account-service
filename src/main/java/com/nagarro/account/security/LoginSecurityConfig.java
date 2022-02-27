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