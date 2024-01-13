package com.hms.www.service;

import com.hms.www.domain.CommentVO;
import com.hms.www.domain.PagingVO;
import com.hms.www.handler.PagingHandler;

public interface CommentService {

	int post(CommentVO cvo);

	PagingHandler getList(int bno, PagingVO pgvo);

	int edit(CommentVO cvo);

	int delete(int cno);
}
