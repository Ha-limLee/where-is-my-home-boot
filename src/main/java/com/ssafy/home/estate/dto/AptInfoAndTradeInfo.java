package com.ssafy.home.estate.dto;

import com.ssafy.home.estate.entity.HouseDeal;
import com.ssafy.home.estate.entity.HouseInfo;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AptInfoAndTradeInfo {
    private HouseInfo BuildingInfo;
    private List<HouseDeal> houseDealList;
}
