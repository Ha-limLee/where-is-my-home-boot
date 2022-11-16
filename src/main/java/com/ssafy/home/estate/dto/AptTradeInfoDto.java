package com.ssafy.home.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AptTradeInfoDto {

	private long aptCode;
	private String aptName;
	private String price;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String lng;
	private String lat;
	
}
