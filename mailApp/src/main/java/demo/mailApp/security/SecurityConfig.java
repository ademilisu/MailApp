package demo.mailApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		 
		 http.csrf().disable();
		 http.authorizeHttpRequests().antMatchers("/index.html", "/app/**", "/3rd/**").permitAll()
		 		.antMatchers("/signup").permitAll()
		 		.antMatchers("/accounts/login").permitAll()
		 		.antMatchers("/accounts/signup").permitAll()
		 		.anyRequest().authenticated()
		 		.and()
		 			.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
		 
		return http.build();
	 }
	 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
