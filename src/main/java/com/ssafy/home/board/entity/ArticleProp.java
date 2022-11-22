package com.ssafy.home.board.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="article_props")
public class ArticleProp {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	private String propName;

	@JsonIgnore
	@OneToMany(mappedBy="articleProp")
	private List<Article> article;
}
