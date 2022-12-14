package com.ssafy.home.estate.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.home.estate.dto.AptSimpleInfoDto;
import com.ssafy.home.estate.entity.*;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.estate.dto.AptTradeInfoDto;
import com.ssafy.home.estate.entity.DongCode;

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

	List<RealEstate> getEstateByKeywordAndLimit(Map<String, Object> options) throws SQLException;

	void addInterestLocation(Map<String, Object> options) throws SQLException;

	List<String> getInterestList(String userId) throws SQLException;

	void deleteInterestLocation(Map<String, Object> options) throws SQLException;

	List<AptSimpleInfoDto> getAptListByLocation(Map<String, Object> options) throws SQLException;

    HouseInfo getAptById(Long aptId) throws SQLException;

	List<HouseDeal> getTradeListByAptId(Long aptId) throws SQLException;

	Business getBusinessById(Long pk) throws SQLException;

	CommercialCode getCommercialCodeBySmallCode(String comCode) throws SQLException;

	RealEstate getEstateById(Long pk) throws SQLException;

	BusStation getBusStationById(Long pk) throws SQLException;

	SubwayStation getSubwayStationById(Long pk) throws SQLException;
}
