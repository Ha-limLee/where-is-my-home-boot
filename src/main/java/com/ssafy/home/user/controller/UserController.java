package com.ssafy.home.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@ApiOperation(value = "회원정보", notes = "회원한명에 대한 정보.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "아이디", required = true, dataType = "String", paramType = "path")
//			@ApiImplicitParam(name = "param1", value = "파라미터1", required = true, dataType = "String", paramType = "query"),
//			@ApiImplicitParam(name = "param2", value = "파마미터2", required = false, dataType = "int", paramType = "query") 
	})
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserInfo(@PathVariable("userId") String userId) {
		
		try {
			User user = userService.getUserInfo(userId);
			return new ResponseEntity<User> (user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("getUserDetail Fail", HttpStatus.NOT_ACCEPTABLE);
		}
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
	
}
