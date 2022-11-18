package com.ssafy.home.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.auth.PrincipalDetails;
import com.ssafy.home.common.JwtProperties;
import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.service.UserService;

import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
///login 요청해서 username, password 전송하면(post)
//UsernamePasswordAuthenticationFilter가 동작을함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	// Authentication 객체를 만들어서 리턴 => 의존 : AuthenticationManager
	// 인증 요청시에 실행되는 함수 => /login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		
		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
		ObjectMapper om = new ObjectMapper();
		
		User user = null;
		try {
			user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			UsernamePasswordAuthenticationToken authenticationToken=
					new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPassword());
			
			// authenticationManager.authenticate()에서 로그인 체크 확정임
			Authentication authentication = 
					authenticationManager.authenticate(authenticationToken);
			
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.println("로그인 완료됨 : " + principalDetails.getUser().getUserId()); // 로그인 정상적으로 되었다는 뜻
			
			return authentication;
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AuthenticationServiceException("Request Content-Type (application/json) Parsing error");
		}
		
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("successfulAuthentication 실행됨:인증이 완료되었다는 뜻임");
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		
		// RSA방식은 아니구 Hash암호방식
				String accessToken = JWT.create()
						.withSubject(principalDetails.getUser().getUserName())
						.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXP_TIME))
						.withClaim("id", principalDetails.getUser().getUserId()) // 비공개claim
						.withClaim("username", principalDetails.getUser().getUserName())
						.withClaim("role", principalDetails.getUser().getRole())
						.sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
				
				response.addHeader(JwtProperties.ACCESS_HEADER_STRING, JwtProperties.TOKEN_HEADER_PREFIX+accessToken);
				
				String refreshToken = JWT.create()
						.withSubject(principalDetails.getUser().getUserName())
						.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXP_TIME))
						.sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
				
				userService.saveRefreshToken(principalDetails.getUser().getUserId(), refreshToken);
				response.addHeader(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_HEADER_PREFIX+refreshToken);

		// 응답 body에 기록
			Map<String, String> responseMap = new HashMap<>();
			responseMap.put(JwtProperties.ACCESS_HEADER_STRING, accessToken);
			responseMap.put(JwtProperties.REFRESH_HEADER_STRING, refreshToken);
			new ObjectMapper().writeValue(response.getWriter(), responseMap);
	}
}
