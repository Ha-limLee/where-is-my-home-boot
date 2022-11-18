package com.ssafy.home.estate.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class AptSimpleInfoDto {
    private long aptCode;
    private String dongCode;
    private int buildyear;
    private String apartmentName;
    private String lng;
    private String lat;
}
