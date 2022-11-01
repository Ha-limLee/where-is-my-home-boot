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

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<User> getUserInfo() {

		return null;
	}

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
				return new ResponseEntity<String>("login Ok", HttpStatus.OK);
			}

			System.out.println("null");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("logout Ok", HttpStatus.OK);
	}
	
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
	
	@DeleteMapping("/remove/{userId}")
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
