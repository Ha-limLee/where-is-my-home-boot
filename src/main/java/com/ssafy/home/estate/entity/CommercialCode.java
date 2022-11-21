package com.ssafy.home.estate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommercialCode {

    long id;
    String bigCode;
    String mediumCode;
    String smallCode;
    String bigName;
    String mediumName;
    String smallName;
}
