package com.ssafy.home.board.service;

import java.util.List;
import java.util.Map;

import com.ssafy.home.board.dto.BoardArticleDto;
import com.ssafy.home.board.entity.Article;
import org.springframework.data.domain.Page;

public interface BoardService {

	public Page<Article> getBoardList(Map<String, String> options) throws Exception;

	public BoardArticleDto getNoticeDetail(String number) throws Exception;

	public void addNotice(Map<String, String> options) throws Exception;

	public void updateNotice(String number, Article notice) throws Exception;

	public void deleteNotice(String number) throws Exception;

	public List<String> getArticleType() throws Exception;
}
