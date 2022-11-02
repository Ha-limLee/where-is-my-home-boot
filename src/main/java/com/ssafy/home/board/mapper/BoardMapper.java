package com.ssafy.home.board.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.entity.Notice;

@Mapper
public interface BoardMapper {

	List<Notice> getBoardList() throws SQLException;

	Notice getNoticeDetail(int num) throws SQLException;

	void addNotice(Notice notice) throws SQLException;

	void increaseHit(int num);

}
