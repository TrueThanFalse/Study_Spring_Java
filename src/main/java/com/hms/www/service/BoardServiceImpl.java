package com.hms.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.www.domain.BoardVO;
import com.hms.www.domain.PagingVO;
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
	public List<BoardVO> getList(PagingVO pgvo) {
		return bdao.selectList(pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		return bdao.selectDetail(bno);
	}

	@Override
	public BoardVO getModify(int bno) {
		bdao.updateReadCountUp(bno);
		return bdao.selectModify(bno);
	}

	@Override
	public int bvoDelete(int bno) {
		return bdao.deleteBvo(bno);
	}

	@Override
	public int edit(BoardVO bvo) {
		return bdao.updateEdit(bvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.selectTotalCount(pgvo);
	} 
	
}
