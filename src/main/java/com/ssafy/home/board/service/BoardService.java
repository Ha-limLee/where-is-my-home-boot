package com.ssafy.home.board.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.ssafy.home.board.dto.BoardArticleDto;
import com.ssafy.home.board.entity.Article;

public interface BoardService {

	public Map<String, Object> getBoardList(Map<String, String> options) throws Exception;

	public BoardArticleDto getNoticeDetail(String number) throws Exception;

	public void addNotice(Map<String, String> options) throws Exception;

	public void updateNotice(String number, Map<String, Object> options) throws Exception;

	public void deleteNotice(String number) throws Exception;

	public List<String> getArticleType() throws Exception;

	public String getDateStr(Timestamp timestamp, String format) throws Exception;

	int getTotalArticleNum() throws Exception;
}
