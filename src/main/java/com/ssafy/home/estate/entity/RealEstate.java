package com.ssafy.home.estate.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RealEstate {

    private long id;
    private String dongCode;
    private String gugunName;
    private String dongName;
    private String address;
    private String registerNumber;
    private String owner;
    private String name;
    private String phoneNumber;
    private String status;
    private int hit;
    private String roadNameCode;

}
