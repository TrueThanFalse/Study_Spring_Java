package com.hms.www.service;

import java.util.List;

import com.hms.www.domain.BoardDTO;
import com.hms.www.domain.BoardVO;
import com.hms.www.domain.PagingVO;

public interface BoardService {

	int insert(BoardDTO bdto);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(int bno);

	BoardVO getModify(int bno);

	int bvoDelete(int bno);

	int edit(BoardVO bvo);

	int getTotalCount(PagingVO pgvo);

}
