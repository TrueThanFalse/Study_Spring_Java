package com.hms.www.service;

import java.util.List;

import com.hms.www.domain.BoardDTO;
import com.hms.www.domain.BoardVO;
import com.hms.www.domain.PagingVO;

public interface BoardService {

	int insert(BoardDTO bdto);

	List<BoardVO> getList(PagingVO pgvo);

	BoardDTO getDetail(int bno);

	BoardDTO getModify(int bno);

	int bvoDelete(int bno);

	int edit(BoardDTO bdto);

	int getTotalCount(PagingVO pgvo);

	int deleteFile(String uuid);

}
