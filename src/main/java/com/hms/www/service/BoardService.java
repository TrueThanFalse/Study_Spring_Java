package com.hms.www.service;

import java.util.List;

import com.hms.www.domain.BoardVO;
import com.hms.www.domain.PagingVO;

public interface BoardService {

	void insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(int bno);

	BoardVO getModify(int bno);

	int bvoDelete(int bno);

	int edit(BoardVO bvo);

	int getTotalCount(PagingVO pgvo);

}
