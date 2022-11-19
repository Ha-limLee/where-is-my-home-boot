package com.ssafy.home.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.home.auth.PrincipalDetails;
import com.ssafy.home.auth.PrincipalDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.JwtProperties;
import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.service.UserService;

import io.jsonwebtoken.io.IOException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@Api("Jwt 처리 API")
@RequiredArgsConstructor
public class JwtController {

	private final UserService userService;
	private final PrincipalDetailsService principalDetailsService;
//	private final JwtService jwtService;

//	@ApiOperation(value = "로그인", notes = "로그인에 대한  API.")
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody Map<String, String> loginInfo,
//			HttpServletResponse response) {
//
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = null;
//		
//		System.out.println("로그인 api 시작");
//		for (String s : loginInfo.keySet()) {
//			System.out.println(s + " : " + loginInfo.get(s));
//		}
//		System.out.println("/users/login");
//		try {
//			User user = userService.loginMember(loginInfo);
//
//			if (user != null) { // 해당 id, password가 존재 할 때
//				String accessToken = jwtService.createAccessToken("userid", user.getUserId());
//				String refreshToken = jwtService.createRefreshToken("userid", user.getUserId());
//
//				userService.saveRefreshToken(user.getUserId(), refreshToken);
//				
//				resultMap.put(JwtProperties.ACCESS_HEADER_STRING, accessToken);
//				resultMap.put(JwtProperties.REFRESH_HEADER_STRING, refreshToken);
//				resultMap.put("message", "success");
//				resultMap.put("user", user);
//				status = HttpStatus.ACCEPTED;
//
//				System.out.println(user.toString());
//			} else { // 해당 id, password가 존재하지 않을 때
//				resultMap.put("message", "fail");
//				status = HttpStatus.ACCEPTED;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultMap.put("message", e.getMessage());
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<>(resultMap, status);
//	}

//	public ResponseEntity<?> checkToken(@CookieValue(value = "token", required = false) String token) {
//		Claims claims = jwtService.checkToken(token);
//		
//		if (claims != null) {
//			String id = claims.get("id").toString();
//			return new ResponseEntity<>(id, HttpStatus.OK);
//		}
//		
//		return new ResponseEntity<>(null, HttpStatus.OK);
//	}

//	@PostMapping("/refresh")
//	public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest req) {
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = HttpStatus.ACCEPTED;
//		String token = req.getHeader(JwtProperties.REFRESH_HEADER_STRING);
//		jwtService.checkToken(token);
//		
//		if (token.equals(userService.getRefreshToken(user.getUserId()))) {
//			String accessToken = jwtService.createAccessToken("userid", user.getUserId());
//			resultMap.put(JwtProperties.ACCESS_HEADER_STRING, accessToken);
//			resultMap.put("message", "success");
//			status = HttpStatus.ACCEPTED;
//		} else {
//			status = HttpStatus.UNAUTHORIZED;
//		}
//		return new ResponseEntity<Map<String, Object>>(resultMap, status);
//	}

	@GetMapping("/token/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(JwtProperties.REFRESH_HEADER_STRING);

		if (authorizationHeader != null && authorizationHeader.startsWith(JwtProperties.TOKEN_HEADER_PREFIX)) {
			try {
				String refreshToken = authorizationHeader.substring(JwtProperties.TOKEN_HEADER_PREFIX.length());
//				Algorithm algorithm = Algorithm.HMAC512(JwtProperties.SECRET_KEY);
//
//				String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_KEY)).build().verify(refreshToken)
//						.getClaim("username").asString();
//
//				User user = userService.getUserByUserName(username);
//
//				String accessToken = JWT.create()
//						.withSubject(user.getUserName())
//						.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXP_TIME))
//						.withClaim("id", user.getUserId()) // 비공개claim
//						.withClaim("username", user.getUserName())
//						.sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));

				Map<String, String> tokens = principalDetailsService.refresh(refreshToken);
//				tokens.put("access_token", accessToken);
//				tokens.put("refresh_token", refreshToken);
				response.setHeader(JwtProperties.ACCESS_HEADER_STRING, JwtProperties.TOKEN_HEADER_PREFIX+tokens.get(JwtProperties.ACCESS_HEADER_STRING));
				if (tokens.get(JwtProperties.REFRESH_HEADER_STRING) != null) {
					response.setHeader(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_HEADER_PREFIX+tokens.get(JwtProperties.REFRESH_HEADER_STRING));
				}
//				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

				return new ResponseEntity<Map<String,String>>(tokens, HttpStatus.OK);
			} catch (Exception e) {
//				response.setHeader("error", e.getMessage());
//				response.setStatus(Forbidden.value());
//
//				Map<String, String> error = new HashMap<>();
//				error.put("error_message", e.getMessage());
//				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), error);
				return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}

	@ApiOperation(value = "로그아웃", notes = "로그아웃에 대한 정보.")
	@PutMapping("/logout")
	public ResponseEntity<?> logout(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User user = principalDetails.getUser();
		if(user == null) {
			throw new RuntimeException("no exist user");
		}

		userService.deleteRefreshToken(user.getUserId());

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		resultMap.put("message", "success");
		status = HttpStatus.ACCEPTED;
//		try {
//			resultMap.put("message", "success");
//			status = HttpStatus.ACCEPTED;
//		} catch (Exception e) {
//			resultMap.put("message", "fail");
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
		return new ResponseEntity<>(resultMap, status);
	}
}
