package com.ssafy.home.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@Api("유저 컨트롤러 API")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "로그인", notes = "로그인에 대한  API.")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map, HttpSession session,
			HttpServletResponse response) {

		System.out.println(".");
		for (String s : map.keySet()) {
			System.out.println(s + " : " + map.get(s));
		}
		System.out.println("/users/login");
		try {
			User user = userService.loginMember(map);

			if (user != null) {
				session.setAttribute("userinfo", user);

				/*
				 * 필요시 쿠키 추가
				 */
				System.out.println(user.toString());
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}

			System.out.println("null");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "로그아웃", notes = "로그아웃에 대한 정보.")
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("logout Ok", HttpStatus.OK);
	}
	
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
	
	@ApiOperation(value = "회원등록", notes = "회원의 정보를 받아 처리.")
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody User user) {
		System.out.println("/join-create");

		try {
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
