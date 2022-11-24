package com.ssafy.home.board.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ssafy.home.board.entity.ArticleProp;
import com.ssafy.home.board.repository.ArticlePropRepository;
import com.ssafy.home.board.repository.ArticleRepository;
import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	private final UserRepository userRepository;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper, ArticleRepository articleRepository, ArticlePropRepository articlePropRepository, UserRepository userRepository) {
		this.boardMapper = boardMapper;
		this.articleRepository = articleRepository;
		this.articlePropRepository = articlePropRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Map<String, Object> getBoardList(Map<String, String> options) throws Exception {
		PageRequest pageRequest = PageRequest.of(Integer.parseInt(options.get("page")), Integer.parseInt(options.get("size")), Sort.by("registerTime").descending());
		Map<String, User> userIdList = new HashMap<>();
		Page<Article> pageArticleList = null;
		Map<Integer, ArticleProp> propMap = new HashMap<>();
		Map<String, Object> result = new HashMap<>();


		ArticleProp articleProp = null;
		List<Article> all = new ArrayList<>();

		List<BoardArticleDto> articleDtoList = new ArrayList<>();

		List<ArticleProp> allProps = articlePropRepository.findAll();
		for(ArticleProp ap : allProps) {
			propMap.put(ap.getId(), ap);
		}

		// 글 속성값이 넘어오지 않았을 떄
		if(options.get("type").equals("default")) {
			pageArticleList = articleRepository.findAll(pageRequest);
			all = pageArticleList.getContent();
		} else {

			for(ArticleProp ap : allProps) {
				if(options.get("type").equals(ap.getPropName())) {
					articleProp = ap;
					break;
				}
			}

			if(articleProp == null) { // 글 속성 값이 테이블에 없을 떄
				pageArticleList = articleRepository.findAll(pageRequest);
				all = pageArticleList.getContent();
			} else { // 글 속성 값이 테이블에 있을 때
				pageArticleList = articleRepository.findByArticleProp(articleProp, pageRequest);
				all = pageArticleList.getContent();
			}
		}

		for(Article a : all) {
			userIdList.put(a.getUserId(), null);
		}

		List<User> userList = userRepository.findByUserIdIn(new ArrayList<>(userIdList.keySet()));

		for(User u : userList) {
			userIdList.put(u.getUserId(), u);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(Article a : all) {
			BoardArticleDto bad = BoardArticleDto.builder()
					.articleNo(a.getArticleNo())
					.userId(a.getUserId())
					.userRole(userIdList.get(a.getUserId()).getRole())
					.subject(a.getSubject())
					.hit(a.getHit())
//					.registerTime(getDateStr(a.getRegisterTime(), BoardProperties.TIME_FORMAT))
					.registerTime(sdf.format(a.getRegisterTime()))
					.articlePropId(a.getArticleProp().getId())
					.articlePropName(propMap.get(a.getArticleProp().getId()).getPropName())
					.build();
			articleDtoList.add(bad);
		}

		result.put("articleList", articleDtoList);
		result.put("maxPage", pageArticleList.getTotalPages());
		result.put("maxSize", pageArticleList.getTotalElements());
		result.put("page", options.get("page"));
		result.put("size", options.get("size"));
		return result;
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
	public void updateNotice(String number, Map<String, Object> options) throws Exception {
		int num = Integer.parseInt(number);
//		Map<String, Object> map = new HashMap<>();
		options.put("num", num);
//		map.put("subject", options.get("subject"));
//		map.put("content", options.get("content"));
		options.put("articleProp", (int)options.get("articleProp"));
		boardMapper.updateNotice(options);
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

	@Override
	public String getDateStr(Timestamp timestamp, String format) throws Exception {
		if ((timestamp==null || timestamp.equals(""))
				|| (format==null || format.equals(""))) return null;

		String strStamp = String.valueOf(timestamp.getTime());
		Date date = new Date(strStamp);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	@Override
	public int getTotalArticleNum() throws Exception {
		return boardMapper.getTotalArticleNum();
	}
}
