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
import com.ssafy.home.estate.dto.DongCode;
import com.ssafy.home.estate.service.EstateService;
import com.ssafy.home.user.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/estate")
@Api("부동산 컨트롤러 API")
public class EstateController {

	private final EstateService estateService;
	
	@Autowired
	public EstateController(EstateService estateService) {
		super();
		this.estateService = estateService;
	}
	
	@ApiOperation(value = "옵션으로 아파트 거래 정보 조회", notes = "[필수]시, 군(구) [선택] 동, 년, 월, 아파트 이름 검색어로 아파트 거래정보 조회.")
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
	@ApiOperation(value = "대한민국 시 목록 조회", notes = "대한민국 시 목록을 전체 조회합니다.")
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
	
	@ApiOperation(value = "시 하나에 포함된 전체 구(군) 목록 조회", notes = "서울에 동작구, 도봉구 ... 처럼 시 하나에 전체 구 를 조회합니다")
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
	
	@ApiOperation(value = "시-구(군)에 해당하는 전체 동 조회", notes = "전체 동 리스트를 조회합니다")
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
	
	@ApiOperation(value = "유저가 DB에 등록한 관심 지역 목록을 전부 가져옵니다", notes = "관심지역 전체 목록 조회 api")
	@GetMapping("/interest")
	public ResponseEntity<?> getInterestLocation(HttpSession session) {
		
		User user = (User)session.getAttribute("userinfo");
		String userId = user.getUserId();
		
		try {
			List<DongCode> dongList = estateService.getInterestLocation(userId);
			return new ResponseEntity<List<DongCode>>(dongList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("get dongList Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
}
