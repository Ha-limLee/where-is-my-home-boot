package com.ssafy.home.estate.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.estate.dto.AptTradeInfoDto;

@Mapper
public interface EstateMapper {

	List<AptTradeInfoDto> getAptListByOption(Map<Object, Object> option) throws SQLException;

	List<String> getSiList() throws SQLException;

	List<String> getGuGunList(String si) throws SQLException;

	List<String> getDongList(Map<String, String> option) throws SQLException;

}
