package com.hms.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.www.domain.BoardVO;
import com.hms.www.repository.BoardDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;

	@Override
	public void insert(BoardVO bvo) {
		bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		return bdao.selectList();
	}

	@Override
	public BoardVO getDetail(int bno) {
		return bdao.selectDetail(bno);
	} 
}
