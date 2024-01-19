package com.hms.www.security;

import java.util.List;

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
public class MemberVO {

	/*
	CREATE TABLE member(
	email VARCHAR(100) NOT NULL,
	pwd VARCHAR(1000) NOT NULL,
	nick_name VARCHAR(100) NOT NULL,
	reg_at DATETIME DEFAULT NOW(),
	last_login DATETIME DEFAULT NULL,
	PRIMARY KEY(email)
	);
	 */
	
	private String email;
	private String pwd;
	private String nickName;
	private String regAt;
	private String lastLogin;
	
	// 한명의 User는 자신만의 권한을 가지고 있어야 함
	// 따라서 MemberVO가 생성되면 항상 AuthVO도 set 되어야 한다. (주의사항)
	private List<AuthVO> authList;
}
