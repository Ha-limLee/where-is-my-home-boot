package com.ssafy.home.board.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private Timestamp registerTime;
}
