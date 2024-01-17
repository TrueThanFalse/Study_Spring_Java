package com.hms.www.repository;

import java.util.List;

import com.hms.www.domain.BoardVO;
import com.hms.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO boardVO);

	List<BoardVO> selectList(PagingVO pgvo);

	BoardVO selectDetail(int bno);

	void updateReadCountUp(int bno);

	BoardVO selectModify(int bno);

	int deleteBvo(int bno);

	int updateEdit(BoardVO bvo);

	int selectTotalCount(PagingVO pgvo);

	long selectOneBno();

}
