package com.products.pragati.controller;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return NoOpPasswordEncoder.getInstance(); // For testing only!
	}

	@Bean
	public UserDetailsService setUserRoles() {
	    var user = User.builder()
	            .username("pragati")
	            .password("123")   // plain text
	            .roles("USER")
	            .build();

	    var admin = User.builder()
	            .username("admin")
	            .password("123")   // plain text
	            .roles("ADMIN")
	            .build();

	    return new InMemoryUserDetailsManager(user, admin);
	}

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity
	        .authorizeHttpRequests(auth -> auth
	        		.requestMatchers(HttpMethod.POST, "/products").hasRole("USER")
	        		.requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	            
	        )
	        .csrf(csrf -> csrf.disable())  
	        .httpBasic();  // Basic Auth enabled

	    return httpSecurity.build();
	}
	
	
	
}
