package com.hms.www.service;

import com.hms.www.security.MemberVO;

public interface MemberService {

	int signUP(MemberVO mvo);

	int selectEmail(String email);

	boolean updateLastLogin(String authEmail);

}
