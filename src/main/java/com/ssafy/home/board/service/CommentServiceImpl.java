package com.ssafy.home.board.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.home.board.dto.UserCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.board.dto.CommentDto;
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
	public List<UserCommentDto> getCommentList(String articleNo) throws Exception {
		int number = Integer.parseInt(articleNo);
//		List<Comment> comment = commentMapper.getCommentList(number);
		List<UserCommentDto> commentList = commentMapper.getCommentList(number);
//		List<CommentDto> commentDtos = new ArrayList<>();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		for(Comment c : comment) {
//			String date = sdf.format(c.getRegisterDate());
//			CommentDto cd = new CommentDto(c.getId(), date, c.getContent(), c.getUserId(), number);
//			commentDtos.add(cd);
//		}
		return commentList;
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
