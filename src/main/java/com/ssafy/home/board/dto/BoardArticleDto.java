package com.ssafy.home.board.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BoardArticleDto {

	private int articleNo;
	private String userId;
	private String userRole;
	private String subject;
	private String content;
	private int hit;
	private String registerTime;
	private int articlePropId;
	private String articlePropName;
	
}
