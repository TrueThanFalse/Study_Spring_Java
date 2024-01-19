package com.hms.www.security;

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
public class AuthVO {

	/*
	CREATE TABLE auth_member(
	email VARCHAR(100) NOT NULL,
	auth VARCHAR(50) NOT NULL);
	 */
	
	private String email;
	private String auth;
	// 권한은 ROLE 단어로 표기해야 한다.
	// e.g.) ROLE_USER, ROLE_ADMIN
}
