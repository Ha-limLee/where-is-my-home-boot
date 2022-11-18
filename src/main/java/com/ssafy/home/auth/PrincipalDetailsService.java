package com.ssafy.home.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ssafy.home.common.JwtProperties;
import com.ssafy.home.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/login => 여기서 동작을 안한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService의 loadUserByUsername()");
		User userEntity = userRepository.findByUserId(username)
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		System.out.println("userEntity:" + userEntity);
		
		if(userEntity != null)
			return new PrincipalDetails(userEntity);
		else
			return null;
	}

	public Map<String, String> refresh(String refreshToken) {

		// === Refresh Token 유효성 검사 === //
		JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_KEY)).build();
		DecodedJWT decodedJWT = verifier.verify(refreshToken);

		// === Access Token 재발급 === //
		long now = System.currentTimeMillis();
		String username = decodedJWT.getSubject();
		User userEntity = userRepository.findByUserId(username)
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		if (!userEntity.getToken().equals(refreshToken)) {
			throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
		}
		String accessToken = JWT.create()
				.withSubject(userEntity.getUserId())
				.withExpiresAt(new Date(now + JwtProperties.ACCESS_EXP_TIME))
				.withClaim("id", userEntity.getUserId()) // 비공개claim
				.withClaim("username", userEntity.getUserName())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
		Map<String, String> accessTokenResponseMap = new HashMap<>();

		// === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
		// === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
		long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;
		long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600); // 남은 일수
		long diffMin = (refreshExpireTime - now) / 1000 / 60; // 남은 분 수
		if (diffDays < 1) { // refreshToken 유효기간이 1일 미만일 때
			String newRefreshToken = JWT.create()
					.withSubject(userEntity.getUserId())
					.withExpiresAt(new Date(now +JwtProperties.REFRESH_EXP_TIME))
					.sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
			accessTokenResponseMap.put(JwtProperties.REFRESH_HEADER_STRING, newRefreshToken);
			userEntity.updateRefreshToken(newRefreshToken);
		}

		accessTokenResponseMap.put(JwtProperties.ACCESS_HEADER_STRING, accessToken);
		return accessTokenResponseMap;
	}
}
