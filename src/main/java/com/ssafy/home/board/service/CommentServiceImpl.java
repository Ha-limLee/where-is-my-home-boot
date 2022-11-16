package com.ssafy.home.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.board.entity.Comment;
import com.ssafy.home.board.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentMapper commentMapper;

	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public List<Comment> getCommentList(String articleNo) throws Exception {
		int number = Integer.parseInt(articleNo);
		List<Comment> comment = commentMapper.getCommentList(number);
		return comment;
	}

	@Override
	public void createComment(Comment comment) throws Exception {
		commentMapper.createComment(comment);
	}

	@Override
	public void deleteComment(String commentNo) throws Exception {
		int number = Integer.parseInt(commentNo);
		commentMapper.deleteComment(number);
	}

	@Override
	public void updateComment(Comment comment) throws Exception {
		commentMapper.updateComment(comment);
	}
	
}
