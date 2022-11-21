package com.ssafy.home.estate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.home.auth.PrincipalDetails;
import com.ssafy.home.estate.dto.*;
import com.ssafy.home.estate.entity.HouseDeal;
import com.ssafy.home.estate.entity.HouseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ssafy.home.estate.service.EstateService;
import com.ssafy.home.user.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/estate")
@Api("부동산 컨트롤러 API")
@Slf4j
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

	@PostMapping("/interest/{dongCode}")
	public ResponseEntity<?> addInterestLocation(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String dongCode) {

		String userId = principalDetails.getUser().getUserId();
		System.out.println(dongCode);
		System.out.println(userId);
		try {
			estateService.addInterestLocation(userId, dongCode);
			return ResponseEntity.ok("insert interest OK");
		} catch(IllegalArgumentException ia) {
			return new ResponseEntity<String>(ia.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("insert interest FAIL", HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/interest/{dongCode}")
	public ResponseEntity<?> deleteInterestLocation(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String dongCode) {

		String userId = principalDetails.getUser().getUserId();
		System.out.println(dongCode);
		System.out.println(userId);
		try {
			estateService.deleteInterestLocation(userId, dongCode);
			return ResponseEntity.ok("delete interest OK");
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("delete interest FAIL", HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping("/region/apartment")
	public ResponseEntity<?> getAptListByRegion(@RequestParam int dongCode) {

		List<AptSimpleInfoDto> aptSimpleInfoDtos = null;
		try {
			aptSimpleInfoDtos = estateService.getAptListByOption(dongCode);
			return ResponseEntity.ok(aptSimpleInfoDtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("에러 발생", HttpStatus.BAD_REQUEST);
		}
	}

	// keyword -> 이름에 해당 keyword가 속한 지하철역, 버스정류장, 아파트, 주변상권 정보(이름, id, 테이블이름) 리턴
	@GetMapping("/building/keyword/{keyword}")
	public ResponseEntity<?> getBuildingListByKeyword(@PathVariable String keyword) {

		Map<String, Object> options = new HashMap<>();

		options.put("keyword", keyword);

		try {
			List<SimpleBuildingDto> buildings = estateService.getBuildingListByKeyword(options);
			return ResponseEntity.ok(buildings);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("keyword리스트 가져오던 중 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 특정 위치(위도, 경도) -> 해당 위도 경도 근처에 있는 아파트 목록 리턴
	@GetMapping("/location/apartment")
	public ResponseEntity<?> getBuildingListByLocation(@RequestParam Map<String, Object> options) {

		try {
			List<AptSimpleInfoDto> aptList = estateService.getAptListByLocation(options);
			return ResponseEntity.ok(aptList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("get aptList from location fail", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/apartment/{aptId}")
	public ResponseEntity<?> getAptInfoAndTradeInfoById(@PathVariable Long aptId) {

		try {
			HouseInfo houseInfo = estateService.getAptById(aptId);
			List<HouseDeal> houseDeals = estateService.getTradeListByAptId(aptId);
			AptInfoAndTradeInfo aptInfoAndTradeInfo = new AptInfoAndTradeInfo(houseInfo, houseDeals);
			return ResponseEntity.ok(aptInfoAndTradeInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("get getAptInfoAndTradeInfoById fail", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/tableName/{tableName}/id/{pk}")
	public ResponseEntity<?> getAptNearBuilding(@PathVariable("tableName") String tableName,
												@PathVariable("pk") Long pk,
												@RequestParam Long distance) {

		try {
			BuildingInfoAndAptList aptList = estateService.getAptNearBuilding(tableName, pk, distance);
			return ResponseEntity.ok(aptList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("get getAptNearBuilding fail", HttpStatus.BAD_REQUEST);
		}
	}
}
