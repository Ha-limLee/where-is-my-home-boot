package com.ssafy.home.board.service;

import java.util.*;

import com.ssafy.home.board.entity.ArticleProp;
import com.ssafy.home.board.repository.ArticlePropRepository;
import com.ssafy.home.board.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.board.dto.BoardArticleDto;
import com.ssafy.home.board.entity.Article;
import com.ssafy.home.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final ArticleRepository articleRepository;
	private final ArticlePropRepository articlePropRepository;

	public BoardServiceImpl(BoardMapper boardMapper, ArticleRepository articleRepository, ArticlePropRepository articlePropRepository) {
		this.boardMapper = boardMapper;
		this.articleRepository = articleRepository;
		this.articlePropRepository = articlePropRepository;
	}

	@Override
	public Page<Article> getBoardList(Map<String, String> options) throws Exception {
		PageRequest pageRequest = PageRequest.of(Integer.parseInt(options.get("page")), Integer.parseInt(options.get("size")));
		if(options.get("type") == null) {
			return articleRepository.findAll(pageRequest);
		} else {
			ArticleProp ap = articlePropRepository.findByPropName(options.get("type")).orElse(null);
			if(ap == null) {
				return articleRepository.findAll(pageRequest);
			}
			return articleRepository.findByArticleProp(ap, pageRequest);
		}
//		return boardMapper.getBoardList(list);
	}

	@Override
	@Transactional
	public BoardArticleDto getNoticeDetail(String number) throws Exception {
		int num = Integer.parseInt(number);
		BoardArticleDto notice = boardMapper.getNoticeDetail(num);
		boardMapper.increaseHit(num);
		return notice;
	}

	@Override
	public void addNotice(Map<String, String> options) throws Exception {
		boardMapper.addNotice(options);
	}

	@Override
	public void updateNotice(String number, Article notice) throws Exception {
		int num = Integer.parseInt(number);
		Map<Object, Object> map = new HashMap<>();
		map.put("num", num);
		map.put("subject", notice.getSubject());
		map.put("content", notice.getContent());
		map.put("articleProp", notice.getArticleProp());
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
