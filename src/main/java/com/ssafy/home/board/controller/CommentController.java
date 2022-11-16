package com.ssafy.home.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.entity.Comment;
import com.ssafy.home.board.service.CommentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/comment")
@Api("게시판 댓글 컨트롤러 API")
public class CommentController {

	private final CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}


	@GetMapping("/article/{no}")
	public ResponseEntity<?> getCommentList(@PathVariable("no") String articleNo) {
		try {
			List<Comment> comments = commentService.getCommentList(articleNo);
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("comment List Fail", HttpStatus.OK);
		}
	}
	
}
