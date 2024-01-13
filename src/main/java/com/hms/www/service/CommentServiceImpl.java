package com.hms.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.www.domain.CommentVO;
import com.hms.www.domain.PagingVO;
import com.hms.www.handler.PagingHandler;
import com.hms.www.repository.CommentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentDAO cdao;
	
	@Override
	public int post(CommentVO cvo) {
		return cdao.insert(cvo);
	}

	@Override
	@Transactional
	/*
	 * @Transactional 어노테이션으로 모든 Logic이 완료될 때까지
	 * DB에 변화가 발생해도 그 정보는 트랜잭션이 완료될 때까지 인식하지 않는다.
	 * 트랜잭션을 사용하는 이유 : totalCount, list 개수는 Method 실행 중
	 * 누군가가 DB에 정보를 추가하거나 삭제할 수 있으므로 Method가 시작되는 시점에
	 * 트랜잭션을 실행시켜서 그 시점의 정보만으로 Method를 완료시키는 것이다.
	 * DB와 직접 연동되는 것은 Service이므로 Controller에서 트랜잭션을
	 * 적용하는 것 보다 Service에서 트랜잭션을 적용시키는 것이 효율적이다.
	 * Method가 끝나면 트랜잭션은 자동으로 완료 처리가 됨
	 * 참고 : 트랜잭션을 RootConfig에서 셋팅 하였으므로 사용할 수 있는 것이다.
	 */
	public PagingHandler getList(int bno, PagingVO pgvo) {
		// 여기서 ph 객체 내부에 cmtList 멤버변수를 setting 하고
		// totalCount 값도 구하여 setting 하자.
		
		int totalCount = cdao.selectOneBnoTotalCount(bno);
		List<CommentVO> list = cdao.selectList(bno, pgvo);
		
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		return ph;
	}
	
	@Override
	public int edit(CommentVO cvo) {
		return cdao.update(cvo);
	}

	@Override
	public int delete(int cno) {
		return cdao.delete(cno);
	} 
}

