package com.hms.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

	/*
	CREATE TABLE file(
	uuid VARCHAR(256) NOT NULL,
	save_dir VARCHAR(256) NOT NULL,
	file_name VARCHAR(256) NOT NULL,
	file_type TINYINT(1) DEFAULT 0,
	bno BIGINT,
	file_size BIGINT,
	reg_at DATETIME DEFAULT NOW(),
	PRIMARY KEY(uuid));
	 */
	
	private String uuid;
	private String saveDir;
	private String fileName;
	private int fileType;
	// 이미지 파일만 fileType을 1로 setter 활용하여 setting할 것임
	private long bno;
	private long fileSize;
	// fileSize는 return Type이 long이므로 반드시 long 타입으로 선언되어야 됨
	private String regAt;
}
