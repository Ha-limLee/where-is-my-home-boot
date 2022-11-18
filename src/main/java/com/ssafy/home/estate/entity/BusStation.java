package com.ssafy.home.estate.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusStation {

    private int id;
    private int arsId;
    private String name;
    private String lng;
    private String lat;

}
