package com.aj.blog.blogappapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.aj.blog.blogappapis.blog.security.CustomUserDetailService;
import com.aj.blog.blogappapis.blog.security.JWTAuthenticationEntryPoint;
import com.aj.blog.blogappapis.blog.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	public static final String[] PUBLIC_URLS = { "/api/v1/auth/login", "/api/v1/auth/register", "/v3/api-docs",
			"/api-docs", "/v3/api-docs/**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**",
			"/swagger-ui.html", "/webjars/**"

	};

	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // disabled CSRF
				.authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers("/api/v1/users").permitAll().requestMatchers("/api/v1/users/*").permitAll()
						.requestMatchers("/api/v1/auth/register/admin").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/v1/category/*/posts").permitAll()
						.requestMatchers("/api/v1/user/*/category/*/posts").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/api/v1/posts/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.PUT, "/api/v1/users/*").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/api/v1/post/image/upload/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers("/api/v1/post/image/**").permitAll()
						.requestMatchers("/api/v1/user/*/post/*/comments").hasAnyRole("ADMIN", "USER")

						.requestMatchers(HttpMethod.DELETE, "/api/v1/comments/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/api/v1/posts/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults());

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

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", corsConfiguration);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(-110);
		return bean;
	}

}
