package com.ssafy.home.estate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SimpleBuildingDto {

    private long pk;
    private String name;
    private String tableName;

    private Double distance;

    private String property;

    private String dongCode;

    private String si;
    private String gugun;
    private String dong;

    private String lng;
    private String lat;
}
