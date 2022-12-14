package com.ssafy.home.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.home.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.user.dto.UserDto;
import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@Api("유저 컨트롤러 API")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	/*@ApiOperation(value = "로그인", notes = "로그인에 대한  API.")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map, HttpSession session,
			HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		System.out.println(".");
		for (String s : map.keySet()) {
			System.out.println(s + " : " + map.get(s));
		}
		System.out.println("/users/login");
		try {
			User user = userService.loginMember(map);

			if (user != null) {
				String accessToken = jwtService.createAccessToken("userid", user.getUserId());
				String refreshToken = jwtService.createRefreshToken("userid", user.getUserId());

				userService.saveRefreshToken(user.getUserId(), refreshToken);
				
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", "success");
				resultMap.put("user", user);
				status = HttpStatus.ACCEPTED;

				System.out.println(user.toString());
			} else {
				resultMap.put("message", "fail");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}*/
	
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
//
//	@PostMapping("/refresh")
//	public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest req) {
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = HttpStatus.ACCEPTED;
//		String token = req.getHeader("refresh-token");
//		jwtService.checkToken(token);
//
//		if (token.equals(userService.getRefreshToken(user.getUserId()))) {
//			String accessToken = jwtService.createAccessToken("userid", user.getUserId());
//			resultMap.put("access-token", accessToken);
//			resultMap.put("message", "success");
//			status = HttpStatus.ACCEPTED;
//		} else {
//			status = HttpStatus.UNAUTHORIZED;
//		}
//		return new ResponseEntity<Map<String, Object>>(resultMap, status);
//	}
	
//	@ApiOperation(value = "로그아웃", notes = "로그아웃에 대한 정보.")
//	@PutMapping("/logout/{userId}")
//	public ResponseEntity<?> logout(@PathVariable("userId") String userId) {
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = HttpStatus.ACCEPTED;
//		try {
//			userService.deleteRefreshToken(userId);
//			resultMap.put("message", "success");
//			status = HttpStatus.ACCEPTED;
//		} catch (Exception e) {
//			resultMap.put("message", "fail");
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<>(resultMap, status);
//	}
	
	@ApiOperation(value = "회원정보", notes = "회원한명에 대한 정보.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "아이디", required = true, dataType = "String", paramType = "path")
//			@ApiImplicitParam(name = "param1", value = "파라미터1", required = true, dataType = "String", paramType = "query"),
//			@ApiImplicitParam(name = "param2", value = "파마미터2", required = false, dataType = "int", paramType = "query") 
	})
	@GetMapping("/user/mypage")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {

		User user = principalDetails.getUser();
		UserDto ud = new UserDto();
		ud.setUserId(user.getUserId());
		ud.setUserName(user.getUserName());
		ud.setAddress(user.getAddress());
		ud.setPhoneNumber(user.getPhoneNumber());
		return new ResponseEntity<UserDto> (ud, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>("getUserDetail Fail", HttpStatus.NOT_ACCEPTABLE);
//		}
	}
	
	@ApiOperation(value = "회원리스트", notes = "민감한 정보(비밀번호 등)를 제외한 회원들 가져오기")
	@GetMapping("/user-public")
	public ResponseEntity<?> getUsers() {
		try {
			List<User> users = userService.getUsers();
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("getUsers Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "회원등록", notes = "회원의 정보를 받아 처리.")
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserDto user) {
		System.out.println("/join-create");

		try {
			user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
			userService.joinUser(user);
			return new ResponseEntity<String>("join OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("join Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@ApiOperation(value = "회원정보수정", notes = "회원정보를 수정합니다.")
	@PutMapping("/join")
	public ResponseEntity<?> update(@RequestBody User user) {
		System.out.println("/join-update");

		try {
			userService.updateUser(user);
			return new ResponseEntity<String>("update OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("update Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@ApiOperation(value = "회원정보삭제", notes = "회원정보를 삭제합니다.")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> remove(@PathVariable("userId") String userId) {
		
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<String>("delete OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("delete Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping("/passwordCheck")
	public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> options, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		User user = principalDetails.getUser();

		String password = options.get("password");

		if(passwordEncoder.matches(password, user.getUserPassword())) {
			return ResponseEntity.ok("ok");
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.UNAUTHORIZED);
		}
	}
}
