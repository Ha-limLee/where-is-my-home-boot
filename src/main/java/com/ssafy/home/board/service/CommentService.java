package com.ssafy.home.board.service;

import java.util.List;

import com.ssafy.home.board.entity.Comment;

public interface CommentService {

	List<Comment> getCommentList(String articleNo) throws Exception;

	void createComment(Comment comment) throws Exception;

	void deleteComment(String commentNo) throws Exception;

	void updateComment(Comment comment) throws Exception;

}
