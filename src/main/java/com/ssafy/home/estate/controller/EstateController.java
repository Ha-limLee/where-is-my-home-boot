package com.ssafy.home.estate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.home.auth.PrincipalDetails;
import com.ssafy.home.estate.dto.AptSimpleInfoDto;
import com.ssafy.home.estate.dto.SimpleBuildingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
	
	@ApiOperation(value = "옵션으로 아파트 거래 정보 조회", notes = "아파트이름, 시, 군, 구, 가격(시작~종료), 기간(시작년월~종료년월)")
	@GetMapping("/trade/apartment")
	public ResponseEntity<?> getAptTradeList(@RequestParam Map<Object, Object> option) {
		
		try {
			List<AptTradeInfoDto> aptList = estateService.getAptTradeListByOption(option);
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
	public ResponseEntity<?> getInterestLocation(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		User user = principalDetails.getUser();
		String userId = user.getUserId();
		
		try {
			List<DongCode> dongList = estateService.getInterestLocation(userId);
			return new ResponseEntity<List<DongCode>>(dongList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("get dongList Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@GetMapping("/region/apartment")
	public ResponseEntity<?> getAptListByRegion(@RequestParam int dongCode) {

		List<AptSimpleInfoDto> aptSimpleInfoDtos = null;
		try {
			aptSimpleInfoDtos = estateService.getAptListByOption(dongCode);
			return ResponseEntity.ok(aptSimpleInfoDtos);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/building/keyword/{keyword}")
	public ResponseEntity<?> getBuildingListByKeyword(@PathVariable String keyword) {

		Map<String, Object> options = new HashMap<>();

		options.put("keyword", keyword);

		try {
			List<SimpleBuildingDto> buildings = estateService.getBuildingListByKeyword(options);
			return ResponseEntity.ok(buildings);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("keyword리스트 가져오던 중 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
