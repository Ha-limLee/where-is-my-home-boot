package com.ssafy.home.board.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.board.entity.Article;
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
	public List<Article> getBoardList(String articleType) throws Exception {
		return boardMapper.getBoardList(articleType);
	}

	@Override
	@Transactional
	public Article getNoticeDetail(String number) throws Exception {
		int num = Integer.parseInt(number);
		Article notice = boardMapper.getNoticeDetail(num);
		boardMapper.increaseHit(num);
		return notice;
	}

	@Override
	public void addNotice(Article notice) throws Exception {
		boardMapper.addNotice(notice);
	}

	@Override
	public void updateNotice(String number, Article notice) throws Exception {
		int num = Integer.parseInt(number);
		Map<Object, Object> map = new HashMap<>();
		map.put("num", num);
		map.put("subject", notice.getSubject());
		map.put("content", notice.getContent());
		boardMapper.updateNotice(map);
	}

	@Override
	public void deleteNotice(String number) throws Exception {
		int num = Integer.parseInt(number);
		boardMapper.deleteNotice(num);
	}

	@Override
	public List<String> getArticleType() throws Exception {
		return boardMapper.getArticleType();
	}
}
