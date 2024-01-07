package com.hms.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	/*
	CREATE TABLE board(
	bno BIGINT NOT NULL AUTO_INCREMENT,
	title VARCHAR(200) NOT NULL,
	writer VARCHAR(100) NOT NULL,
	content TEXT NOT NULL,
	reg_at DATETIME DEFAULT NOW(),
	mod_at DATETIME DEFAULT NOW(),
	read_count INT DEFAULT 0,
	cmt_qty INT DEFAULT 0,
	has_file INT DEFAULT 0,
	PRIMARY KEY(bno));
	*/
	
	private long bno;
	private String title;
	private String writer;
	private String content;
	// DB에서 _(언더바)를 사용한 컬럼명을 Java에서 작성할 때 카멜표기법으로 작성해야 됨
	// 왜? MybatisConfig.xml에서 카멜표기법으로 자동 변환되도록 설정 하였으므로
	private String regAt;
	private String modAt;
	private int readCount;
	private int cmtQty;
	private int hasFile;
	
}
