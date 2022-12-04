package com.ssafy.home.board.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.home.board.dto.UserCommentDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.board.entity.Comment;

@Mapper
public interface CommentMapper {

	List<UserCommentDto> getCommentList(int number) throws SQLException;

	void createComment(Comment comment) throws SQLException;

	void deleteComment(int number) throws SQLException;

	void updateComment(Comment comment) throws SQLException;
	
}
