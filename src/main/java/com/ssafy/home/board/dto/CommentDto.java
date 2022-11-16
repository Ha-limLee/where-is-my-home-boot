package com.ssafy.home.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {

	private int id;
	private String registerDate;
	private String content;
	private String userId;
	private int articleId;
}
