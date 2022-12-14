package com.ssafy.home.board.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.dto.BoardArticleDto;
import com.ssafy.home.board.entity.Article;

@Mapper
public interface BoardMapper {

	List<BoardArticleDto> getBoardList(String articleType) throws SQLException;

	BoardArticleDto getNoticeDetail(int num) throws SQLException;

	void addNotice(Map<String, String> options) throws SQLException;

	void increaseHit(int num) throws SQLException;

	void updateNotice(Map<String, Object> map) throws SQLException;

	void deleteNotice(int num) throws SQLException;

	List<String> getArticleType() throws SQLException;

    int getTotalArticleNum() throws SQLException;
}
