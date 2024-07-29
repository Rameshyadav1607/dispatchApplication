package com.iocl.dispatchapplication.config;
import java.net.http.HttpClient;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
		
	}
	  @Bean
	    public HttpClient httpClient() {
	        return HttpClient.newBuilder()
	                .version(HttpClient.Version.HTTP_2)
	                .build();
	    }
	@Bean
	public AuthenticationManager authenticationManager() {
		return new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
	}
//	 @Bean
//	    public AuthenticationManager authenticationManagerBean() throws Exception {
//	        return super.authenticationManagerBean() ;
//	    }
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.authorizeHttpRequests()
//				.requestMatchers("/welcome","/captch/check-captcha","/captch/get-captcha")
//				.permitAll()
				.anyRequest()
				.permitAll()
				
//				.authenticated()
				.and()
				.build();
		
	}

}
