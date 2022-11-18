package com.ssafy.home.estate.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Business {

    private int id;
    private String name;
    private String smallCommercialCode;
    private int zipcode;
    private String lat;
    private String lng;
    private String dongCode;
    private String dongName;
    private String address;
    private String roadCode;

}
