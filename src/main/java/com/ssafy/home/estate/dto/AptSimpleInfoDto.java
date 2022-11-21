package com.ssafy.home.estate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AptSimpleInfoDto {
    private long aptCode;
    private String dongCode;
    private int buildYear;
    private String apartmentName;
    private String lng;
    private String lat;
    private Double distance;
}
