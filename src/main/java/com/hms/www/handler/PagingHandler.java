package com.hms.www.handler;

import com.hms.www.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingHandler {

	private int startPage; // 페이지네이션의 시작 번호 => 1, 11, 21...
	private int endPage; // 페이지네이션의 끝 번호 => 10, 20, 30...
	private boolean prev, next; // 페이지네이션 이전,다음 버튼 활성화 여부
	
	private int totalCount; // 총 게시글 수 => 매개변수로 setting
	private PagingVO pgvo; // 매개변수로 setting
	
	// 생성자에서 모든 Logic이 구현되어 모든 값이 설정되어야 함
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		/*
		startPage~endPage
		1~10 / 11~20 / 21~30...
		1부터 10까지 endPage는 변함없이 10이 되어야 함
		1 2 3 4 ... 10 / 10 나머지를 올림(ceil)하여 1로 만들고 곱하기 10
		*/
		this.endPage = (int)Math.ceil(pgvo.getPageNo() / (double)pgvo.getQty()) * 10;
		// 소수점을 인식할 수 있도록 pgvo.getPageNo() 또는 pgvo.getQty() 중 하나를 double로 형변환
		this.startPage = endPage - 9;
		
		// 실제 마지막 페이지
		// 올림(전체 글 수 / 한 페이지에 표시되는 게시글 수)
		int realEndPage = (int)Math.ceil(totalCount / (double)pgvo.getQty());
		
		if(realEndPage < endPage) {
			this.endPage = realEndPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	
}
