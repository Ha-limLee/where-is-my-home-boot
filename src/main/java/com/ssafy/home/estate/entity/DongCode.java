package com.ssafy.home.estate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="dongcode")
@Entity
public class DongCode {

	@Id
	private String dongCode;

	private String sidoName;

	private String gugunName;

	private String dongName;
}
