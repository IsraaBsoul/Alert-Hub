package com.example.Security.sec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Security.Client.UserClient;
import com.example.Security.dto.UserDto;
import com.example.Security.model.UserRole;
import com.example.Security.repository.UserRoleRepository;


@Configuration
public class SecurityConfig {

	@Autowired
    private UserClient userClient;
	@Autowired
    private UserRoleRepository userRoleRepository;

    // Spring Security filter chain
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter ) throws Exception
	{
		http.csrf(csrf -> csrf.disable())
	    .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**").permitAll()  // <-- match your controller
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		
		
		
		return http.build();
	}
		

    // Load user details from User service + map roles from Security service
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserDto userDto = userClient.getUserByEmail(email); // call User service
            if (userDto == null) {
                throw new UsernameNotFoundException("User not found in User service");
            }

            List<UserRole> roles = userRoleRepository.findByUserId(userDto.getId());

            return org.springframework.security.core.userdetails.User
                    .withUsername(userDto.getEmail())
                    .password(userDto.getPassword()) // password should be hashed
                    .authorities(
                        roles.stream()
                             .map(r -> r.getRole().getRoleName())
                             .toArray(String[]::new)
                    )
                    .build();
        };
    }

    // Authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Password encoder for hashing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}