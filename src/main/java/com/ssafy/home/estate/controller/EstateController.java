package com.ssafy.home.estate.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.entity.Notice;
import com.ssafy.home.estate.dto.AptTradeInfoDto;
import com.ssafy.home.estate.service.EstateService;
import com.ssafy.home.user.entity.User;

@RestController
@RequestMapping("/estate")
public class EstateController {

	private final EstateService estateService;
	
	@Autowired
	public EstateController(EstateService estateService) {
		super();
		this.estateService = estateService;
	}
	
	@GetMapping("/apartment")
	public ResponseEntity<?> getAptList(@RequestParam Map<Object, Object> option) {
		
		try {
			if(option.containsKey("dealYear")) {
				option.put("dealYear", Integer.parseInt((String) option.get("dealYear")));
			}
			if(option.containsKey("dealMonth")) {
				option.put("dealMonth", Integer.parseInt((String) option.get("dealMonth")));
			}
			List<AptTradeInfoDto> aptList = estateService.getAptListByOption(option);
			return new ResponseEntity<List<AptTradeInfoDto>>(aptList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("get aptList Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	// 전체 시 목록 가져오기
	@GetMapping("/si")
	public ResponseEntity<?> getSiList() {
		
		try {
			List<String> siList = estateService.getSiList();
			return new ResponseEntity<List<String>>(siList, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("get siList Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<?> getGunGuList(@RequestParam String si) {
		
		try {
			List<String> gugunList = estateService.getGuGunList(si);			
			return new ResponseEntity<List<String>>(gugunList, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("get gugunList Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@GetMapping("/dong")
	public ResponseEntity<?> getdongList(@RequestParam Map<String,String> map) {
		
		try {
			List<String> dongList = estateService.getDongList(map);
			return new ResponseEntity<List<String>>(dongList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("get dongList Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public ResponseEntity<?> getInterestLocation(HttpSession session) {
		
		User user = (User)session.getAttribute("userinfo");
		String userId = user.getUserId();
		
		try {
			estateService.getInterestLocation(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
