package com.ssafy.home.board.service;

import java.util.List;

import com.ssafy.home.board.entity.Notice;

public interface BoardService {

	public List<Notice> getBoardList() throws Exception;

	public Notice getNoticeDetail(String number) throws Exception;

	public void addNotice(Notice notice) throws Exception;

	public void updateNotice(String number, Notice notice) throws Exception;
}
