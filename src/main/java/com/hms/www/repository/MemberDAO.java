package com.hms.www.repository;

import com.hms.www.security.MemberVO;

public interface MemberDAO {

	int insertUser(MemberVO mvo);

	int insertAuth(String email);

	int selectEmail(String email);

}
