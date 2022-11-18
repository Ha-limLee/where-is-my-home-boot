package com.ssafy.home.estate.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.home.estate.dto.AptSimpleInfoDto;
import com.ssafy.home.estate.entity.BusStation;
import com.ssafy.home.estate.entity.Business;
import com.ssafy.home.estate.entity.HouseInfo;
import com.ssafy.home.estate.entity.SubwayStation;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.estate.dto.AptTradeInfoDto;
import com.ssafy.home.estate.dto.DongCode;

@Mapper
public interface EstateMapper {

	List<AptTradeInfoDto> getAptTradeListByOption(Map<Object, Object> option) throws SQLException;

	List<String> getSiList() throws SQLException;

	List<String> getGuGunList(String si) throws SQLException;

	List<String> getDongList(Map<String, String> option) throws SQLException;

	List<DongCode> getInterestLocation(String userId) throws SQLException;

	List<AptSimpleInfoDto> getAptListByOption(int dongCode) throws SQLException;

	List<BusStation> getBusStationByKeywordAndLimit(Map<String, Object> option) throws SQLException;
	List<SubwayStation> getSubwayStationByKeywordAndLimit(Map<String, Object> option) throws SQLException;
	List<HouseInfo> getAptByKeywordAndLimit(Map<String, Object> option) throws SQLException;
	List<Business> getBusinessByKeywordAndLimit(Map<String, Object> option) throws SQLException;
}
