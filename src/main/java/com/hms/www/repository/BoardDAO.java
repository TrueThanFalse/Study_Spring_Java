package com.hms.www.repository;

import java.util.List;

import com.hms.www.domain.BoardVO;

public interface BoardDAO {

	void insert(BoardVO bvo);

	List<BoardVO> selectList();

	BoardVO selectDetail(int bno);

}
