package com.ssafy.home.board.service;

import java.util.List;

import com.ssafy.home.board.entity.Comment;

public interface CommentService {

	List<Comment> getCommentList(String articleNo) throws Exception;

}
