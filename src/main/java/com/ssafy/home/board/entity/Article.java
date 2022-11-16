package com.ssafy.home.board.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {

	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private Timestamp registerTime;
	private int articleProp;
//	private int articleTypeId;
//	private String articleTypeName;
}
