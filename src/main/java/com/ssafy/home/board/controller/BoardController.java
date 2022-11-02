package com.ssafy.home.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.entity.Notice;
import com.ssafy.home.board.service.BoardService;
import com.ssafy.home.board.service.BoardServiceImpl;

@RestController
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardServiceImpl boardService) {
		this.boardService = boardService;
	}

	@GetMapping
	public ResponseEntity<?> getNoticeList() {
		try {
			List<Notice> boardList = boardService.getBoardList();
			return new ResponseEntity<List<Notice>>(boardList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("get Board Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/{noticeNum}")
	public ResponseEntity<?> getNoticeDetail(@PathVariable("noticeNum") String number) {
		try {
			Notice notice = boardService.getNoticeDetail(number);
			return new ResponseEntity<Notice>(notice, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("get Detail Notice Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping
	public ResponseEntity<?> addNotice(@RequestBody Notice notice) {
		try {
			boardService.addNotice(notice);
			return new ResponseEntity<String>("addNotice OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("addNotice Faile", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping("/{noticeNum}")
	public ResponseEntity<?> updateNotice(@PathVariable("noticeNum") String number, @RequestBody Notice notice) {

		try {
			boardService.updateNotice(number, notice);
			return new ResponseEntity<String>("updateNotice OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("updateNotice Fail", HttpStatus.OK);
		}
	}

	@DeleteMapping("/{noticeNum}")
	public ResponseEntity<?> deleteNotice(@PathVariable("noticeNum") String number) {

		try {
			boardService.deleteNotice(number);
			return new ResponseEntity<String>("deleteNotice OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("deleteNotice Fail", HttpStatus.OK);
		}

	}
}
