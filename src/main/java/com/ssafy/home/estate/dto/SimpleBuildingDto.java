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

    long pk;
    String name;
    String tableName;

    Double distance;

    private String property;

    public SimpleBuildingDto(long pk, String name, String tableName) {
        this.pk = pk;
        this.name = name;
        this.tableName = tableName;
    }
}