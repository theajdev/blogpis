package com.aj.blog.blogappapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aj.blog.blogappapis.blog.security.CustomUserDetailService;
import com.aj.blog.blogappapis.blog.security.JWTAuthenticationEntryPoint;
import com.aj.blog.blogappapis.blog.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // disabled CSRF
				.authorizeHttpRequests((auth) -> {
					auth.requestMatchers("/api/v1/auth/login").permitAll();
					auth.requestMatchers("/api/users").hasAnyRole("ADMIN", "USER");
					// auth.requestMatchers("/api/").hasAnyRole("ADMIN", "USER");
					auth.requestMatchers(HttpMethod.DELETE, "/api/categories/").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
					auth.anyRequest().authenticated();
				}).httpBasic(Customizer.withDefaults());

		http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		http.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // allow all
																									// requests
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@SuppressWarnings("removal")
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(customUserDetailService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
