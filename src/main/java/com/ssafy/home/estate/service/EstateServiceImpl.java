package com.ssafy.home.estate.service;

import java.util.*;

import com.ssafy.home.estate.dto.AptSimpleInfoDto;
import com.ssafy.home.estate.dto.SimpleBuildingDto;
import com.ssafy.home.estate.entity.BusStation;
import com.ssafy.home.estate.entity.Business;
import com.ssafy.home.estate.entity.HouseInfo;
import com.ssafy.home.estate.entity.SubwayStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.estate.dto.AptTradeInfoDto;
import com.ssafy.home.estate.dto.DongCode;
import com.ssafy.home.estate.mapper.EstateMapper;

@Service
public class EstateServiceImpl implements EstateService {

	private final EstateMapper estateMapper;
	
	@Autowired
	public EstateServiceImpl(EstateMapper estateMapper) {
		this.estateMapper = estateMapper;
	}


	@Override
	public List<AptTradeInfoDto> getAptTradeListByOption(Map<Object, Object> option) throws Exception {
		return estateMapper.getAptTradeListByOption(option);
	}


	@Override
	public List<String> getSiList() throws Exception {
		return estateMapper.getSiList();
	}


	@Override
	public List<String> getGuGunList(String si) throws Exception {
		return estateMapper.getGuGunList(si);
	}


	@Override
	public List<String> getDongList(Map<String,String> map) throws Exception {
		return estateMapper.getDongList(map);
	}


	@Override
	public List<DongCode> getInterestLocation(String userId) throws Exception {
		return estateMapper.getInterestLocation(userId);
	}

	@Override
	public List<AptSimpleInfoDto> getAptListByOption(int dongCode) throws Exception {
		return estateMapper.getAptListByOption(dongCode);
	}

	@Override
	public List<SimpleBuildingDto> getBuildingListByKeyword(Map<String, Object> options) throws Exception {

		PriorityQueue<SimpleBuildingDto> pq = new PriorityQueue<>(new Comparator<SimpleBuildingDto>(){

			@Override
			public int compare(SimpleBuildingDto o1, SimpleBuildingDto o2) {
				return o1.getName().length() - o2.getName().length();
			}
		});

		int limit = 100;
		int resultSize = 20;
		options.put("limit", limit);

		// 제일 keyword와 비슷한 subwayStation 객체 추출하기
		List<SubwayStation> sStations = estateMapper.getSubwayStationByKeywordAndLimit(options);
		System.out.println("sStations : " + sStations.size());
		for (SubwayStation s: sStations) {
			SimpleBuildingDto sbd = new SimpleBuildingDto((long)s.getId(), s.getName(), "SubwayStation");
			pq.offer(sbd);
		}

		// 제일 keyword와 비슷한 busStation 객체 추출하기
		List<BusStation> bStations = estateMapper.getBusStationByKeywordAndLimit(options);
		System.out.println("bStations : " + bStations.size());
		for (BusStation b: bStations) {
			SimpleBuildingDto sbd = new SimpleBuildingDto((long)b.getId(), b.getName(), "BusStation");
			pq.offer(sbd);
		}

		// 제일 keyword와 비슷한 business 객체 추출하기
		List<Business> businessList = estateMapper.getBusinessByKeywordAndLimit(options);
		System.out.println("businessList : " + businessList.size());
		for (Business b: businessList) {
			SimpleBuildingDto sbd = new SimpleBuildingDto((long)b.getId(), b.getName(), "Business");
			pq.offer(sbd);
		}

		// 제일 keyword와 비슷한 apt 객체 추출하기
		List<HouseInfo> aptList = estateMapper.getAptByKeywordAndLimit(options);
		System.out.println("aptList : " + aptList.size());
		for (HouseInfo h: aptList) {
			SimpleBuildingDto sbd = new SimpleBuildingDto((long)h.getAptCode(), h.getApartmentName(), "HouseInfo");
			pq.offer(sbd);
		}

		List<SimpleBuildingDto> results = new ArrayList<>();

		for(int i=0; i<resultSize; i++) {
			if(!pq.isEmpty()) {
				results.add(pq.poll());
			}
		}

		return results;
	}

}
