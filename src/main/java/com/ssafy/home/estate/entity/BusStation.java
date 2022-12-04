package com.ssafy.home.estate.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class BusStation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int arsId;
    private String name;
    private String lng;
    private String lat;

}
