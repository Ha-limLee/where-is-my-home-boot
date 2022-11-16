package com.ssafy.home.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AptTradeInfoDto {

	private String aptName;
	private long aptCode;
	private int buildYear;
	private String dongCode;
	private String lng;
	private String lat;
	private String dong;
	private String price;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String area;
	private String floor;
	
}
