package com.ssafy.home.estate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<AptTradeInfoDto> getAptListByOption(Map<Object, Object> option) throws Exception {
		return estateMapper.getAptListByOption(option);
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

}
