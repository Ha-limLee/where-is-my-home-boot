package com.ssafy.home.estate.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BuildingInfoAndAptList {

    private SimpleBuildingDto simpleBuildingDto;
    private List<AptSimpleInfoDto> aptList;
}
