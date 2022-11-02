package com.ssafy.home.estate.service;

import java.util.List;
import java.util.Map;

import com.ssafy.home.estate.dto.AptTradeInfoDto;

public interface EstateService {

	List<AptTradeInfoDto> getAptListByOption(Map<Object, Object> option) throws Exception;

	List<String> getSiList() throws Exception;

	List<String> getGuGunList(String si) throws Exception;

	List<String> getDongList(Map<String,String> map) throws Exception;

	void getInterestLocation(String userId) throws Exception;

}
