package com.ssafy.home.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.board.dto.BoardArticleDto;
import com.ssafy.home.board.entity.Article;
import com.ssafy.home.board.service.BoardService;
import com.ssafy.home.board.service.BoardServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러 API")
public class BoardController {

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardServiceImpl boardService) {
		this.boardService = boardService;
	}

	@ApiOperation(value = "글 전체 조회", notes = "글 전체 조회 API.")
	@GetMapping
	public ResponseEntity<?> getBoardList(@RequestParam(value="page", defaultValue="0") Integer page
			, @RequestParam(value="size", defaultValue="10") Integer size, @RequestParam(value="type", defaultValue="default") String type) {
		Map<String, String> options = new HashMap<>();
		options.put("page", String.valueOf(page));
		options.put("size", String.valueOf(size));
		options.put("type", type);

		try {
			Map<String, Object> result = boardService.getBoardList(options);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("get Board Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@ApiOperation(value = "공지사항 글 하나 조회", notes = "공지사항 글 하나만 조회 API.")
	@GetMapping("/{noticeNum}")
	public ResponseEntity<?> getNoticeDetail(@PathVariable("noticeNum") String number) {
		try {
			BoardArticleDto notice = boardService.getNoticeDetail(number);
			return new ResponseEntity<BoardArticleDto>(notice, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("get Detail Notice Fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@ApiOperation(value = "공지사항 글 추가", notes = "공지사항 글 추가 API.")
	@PostMapping
	public ResponseEntity<?> addNotice(@RequestBody Map<String, String> options) {
		try {
			boardService.addNotice(options);
			return new ResponseEntity<String>("addNotice OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("addNotice Faile", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@ApiOperation(value = "공지사항 글 수정", notes = "공지사항 글 수정 API.")
	@PutMapping("/{noticeNum}")
	public ResponseEntity<?> updateNotice(@PathVariable("noticeNum") String number, @RequestBody Article notice) {

		try {
			boardService.updateNotice(number, notice);
			return new ResponseEntity<String>("updateNotice OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("updateNotice Fail", HttpStatus.OK);
		}
	}

	@ApiOperation(value = "공지사항 글 삭제", notes = "공지사항 글 삭제 API.")
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
	
	@GetMapping("/article/type")
	public ResponseEntity<?> getArticleType() {
		
		try {
			List<String> typeList = boardService.getArticleType();
			return new ResponseEntity<List<String>>(typeList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("article Type Fail", HttpStatus.OK);
		}
	}
	
}
