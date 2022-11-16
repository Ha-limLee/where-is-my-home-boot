package com.ssafy.home.board.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

	private int id;
	private Timestamp registerDate;
	private Timestamp modifyDate;
	private String content;
	private String userId;
	private int articleId;
}
