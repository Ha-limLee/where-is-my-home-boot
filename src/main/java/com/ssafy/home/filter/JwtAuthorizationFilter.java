package com.ssafy.home.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.home.auth.PrincipalDetails;
import com.ssafy.home.common.JwtProperties;
import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.repository.UserRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// 시큐리티가 filter를 가지고 있는데 그 필터 중에 BasicAuthenticationFilter라는 것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있음
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 안타요.
//인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("JwtAuthorization 시작");
		String servletPath = request.getServletPath();
		String header = request.getHeader(JwtProperties.ACCESS_HEADER_STRING);
		System.out.println(servletPath);
		// header가 있는지 확인
		if (servletPath.equals("/users/login") || servletPath.equals("/users/token/refresh")) {

			chain.doFilter(request, response);
		} else if(header == null || !header.startsWith(JwtProperties.TOKEN_HEADER_PREFIX)) {
			// 토큰값이 없거나 정상적이지 않다면 400 오류

			chain.doFilter(request, response);
                     return;

		} else {

			try {
				System.out.println("header : " + header);

				//JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
				String token = request.getHeader(JwtProperties.ACCESS_HEADER_STRING)
						.replace("Bearer ", "");

				// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
				// 내가 SecurityContext에 직접접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
				String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_KEY)).build().verify(token)
						.getClaim("username").asString();

				// 서명이 정상적으로 됨
				if (username != null) {
					User user = userRepository.findByUserName(username)
							.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
					System.out.println(user);
					// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
					// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
					// Jwt토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
					PrincipalDetails principalDetails = new PrincipalDetails(user);
					Authentication authentication =
							new UsernamePasswordAuthenticationToken(
									principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
									null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
									principalDetails.getAuthorities());

					System.out.println("principal : " + principalDetails);
					for (GrantedAuthority authentication2 : principalDetails.getAuthorities()) {
						System.out.println(authentication2.toString());
					}

					// 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

				chain.doFilter(request, response);
			} catch (TokenExpiredException e) {
				logger.info("CustomAuthorizationFilter : Access Token이 만료되었습니다.");
				response.setContentType(APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("utf-8");
//				ErrorResponse errorResponse = new ErrorResponse(401, "Access Token이 만료되었습니다.");
				new ObjectMapper().writeValue(response.getWriter(), new ResponseEntity<String>("Access Token이 만료되었습니다.", HttpStatus.UNAUTHORIZED));
			} catch (Exception e) {
				logger.info("CustomAuthorizationFilter : JWT 토큰이 잘못되었습니다. message : " + e.getMessage());
//				response.setStatus(SC_BAD_REQUEST);
				response.setContentType(APPLICATION_JSON_VALUE);
				response.setCharacterEncoding("utf-8");
//				ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
				new ObjectMapper().writeValue(response.getWriter(), new ResponseEntity<String>("Access Token이 만료되었습니다.", HttpStatus.BAD_REQUEST));
			}
		}
	}

}