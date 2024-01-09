package com.hms.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingVO {

	private int pageNo; // 현재 페이지 번호
	private int qty; // 한 페이지당 표기될 BoardVO 개수
	
	// 검색 관련 멤버변수
	private String type;
	private String keyword;
	
	// default 생성자
	public PagingVO() {
		this.pageNo = 1;
		this.qty = 10;
	}
	
	// 생성자
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	/*
	시작 번지 구하기 (getter 형식)
	
	목적 : list.jsp에 BoardVO를 10개씩 끊어서 출력
	SQL 구문 : SELECT * FROM board ORDER BY bno DESC LIMIT 0,10
	MySQL CMD창에서 위 구문을 실행시켜서 올바른 구문인지 확인해 주세요.
	참고 : LIMIT 시작번지,개수
	
	<< 1 2 3 4 5 6 7 8 9 10 >> (페이지네이션 모양)
	1 페이지 입장, LIMIT 0,10 => (pageNo - 1) * 10 = 0
	2 페이지 입장, LIMIT 10,10 => (2 - 1) * 10 = 10
	3 페이지 입장, LIMIT 20,10 => (3 - 1) * 10 = 20
	...
	*/
	public int getPageStart() {
		return (this.pageNo-1) * qty;
	}
	
	// 검색할 때 type의 값을 배열로 return하는 Method
	// 복합 검색일 때 키워드마다 따로따로 검색해야 하므로 배열이 필요
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
}