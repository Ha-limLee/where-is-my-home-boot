package com.ssafy.home.board.service;

import java.util.List;

import com.ssafy.home.board.dto.BoardArticleDto;
import com.ssafy.home.board.entity.Article;

public interface BoardService {

	public List<BoardArticleDto> getBoardList(String articleType) throws Exception;

	public BoardArticleDto getNoticeDetail(String number) throws Exception;

	public void addNotice(Article notice) throws Exception;

	public void updateNotice(String number, Article notice) throws Exception;

	public void deleteNotice(String number) throws Exception;

	public List<String> getArticleType() throws Exception;
}
