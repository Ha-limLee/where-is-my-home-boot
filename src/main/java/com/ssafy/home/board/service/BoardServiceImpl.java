package com.ssafy.home.board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.board.entity.Notice;
import com.ssafy.home.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	@Override
	public List<Notice> getBoardList() throws Exception {
		return boardMapper.getBoardList();
	}

	@Override
	@Transactional
	public Notice getNoticeDetail(String number) throws Exception {
		int num = Integer.parseInt(number);
		Notice notice = boardMapper.getNoticeDetail(num);
		boardMapper.increaseHit(num);
		return notice;
	}

	@Override
	public void addNotice(Notice notice) throws Exception {
		boardMapper.addNotice(notice);
	}
}
