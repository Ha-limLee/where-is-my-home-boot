package com.ssafy.home.board.entity;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="board")
public class Article {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int articleNo;

	private String userId;
	private String subject;
	private String content;
	private int hit;
	private Timestamp registerTime;

	@ManyToOne(fetch = FetchType.LAZY)
//	@ManyToOne
	@JoinColumn(name = "article_prop")
	private ArticleProp articleProp;

	public void setArticleProp(ArticleProp articleProp) {
		this.articleProp = articleProp;
	}

}
