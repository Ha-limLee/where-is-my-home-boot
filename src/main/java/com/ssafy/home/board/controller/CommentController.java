package com.ssafy.home.board.controller;

import java.util.List;

import com.ssafy.home.board.dto.UserCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.dto.CommentDto;
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
			List<UserCommentDto> comments = commentService.getCommentList(articleNo);
			return ResponseEntity.ok(comments);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("comment List Fail", HttpStatus.OK);
		}
	}
	
	@PostMapping("/article/{no}")
	public ResponseEntity<?> createComment(@PathVariable("no") String articleNo, @RequestBody Comment comment) {
		
		try {
			commentService.createComment(comment);
			return new ResponseEntity<String>("create Comment OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("create Comment Fail", HttpStatus.OK);
		}
	}
	
	@PutMapping("/{no}")
	public ResponseEntity<?> updateComment(@PathVariable("no") String commentNo, @RequestBody Comment comment) {
		try {
			commentService.updateComment(comment);
			return new ResponseEntity<String>("update Comment OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("update Comment Fail", HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{no}")
	public ResponseEntity<?> deleteComment(@PathVariable("no") String commentNo) {
		
		try {
			commentService.deleteComment(commentNo);
			return new ResponseEntity<String>("delete Comment OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("delete Comment Fail", HttpStatus.OK);
		}
	}
}
