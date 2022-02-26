package com.nagarro.account.config;

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

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password(this.passwordEncoder().encode("admin"))
				.roles("ADMIN");

		auth.inMemoryAuthentication()
				.withUser("user")
				.password(this.passwordEncoder().encode("user"))
				.roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

		http.sessionManagement().maximumSessions(1);

		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/account/search").access("hasRole('USER') or hasRole('ADMIN')")
				.antMatchers(HttpMethod.GET, "/account/search/filter").access("hasRole('ADMIN')")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}