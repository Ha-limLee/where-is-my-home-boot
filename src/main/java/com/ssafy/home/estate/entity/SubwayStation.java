package com.ssafy.home.estate.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubwayStation {

    private int id;
    private int line;
    private int number;
    private String name;
    private String lat;
    private String lng;

}
