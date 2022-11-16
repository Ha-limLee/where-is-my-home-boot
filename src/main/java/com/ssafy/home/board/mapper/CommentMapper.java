package com.ssafy.home.board.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.entity.Comment;

@Mapper
public interface CommentMapper {

	List<Comment> getCommentList(int number) throws SQLException;
	
}
