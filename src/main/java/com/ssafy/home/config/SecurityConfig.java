package com.ssafy.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.ssafy.home.filter.JwtAuthenticationFilter;
import com.ssafy.home.filter.JwtAuthorizationFilter;
import com.ssafy.home.user.repository.UserRepository;
import com.ssafy.home.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserRepository userRepository;
	private final UserService userService;
	private final CorsConfig corsConfig;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), userService);
		jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");
		
		http
			.addFilter(corsConfig.corsFilter())
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.addFilter(jwtAuthenticationFilter)
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
			.authorizeRequests() //보안 검사기능 시작
			.antMatchers("/")
			.authenticated()
			.antMatchers("/api/v1/user/**")
//			.access("hasRole('ROLE_admin') or hasRole('ROLE_user')")
			.hasAnyAuthority("admin","user")
			.antMatchers("/api/v1/admin/**")
//				.access("hasRole('admin')")
			.hasAuthority("admin")
			.anyRequest().permitAll();
			
		
	}
	
}
