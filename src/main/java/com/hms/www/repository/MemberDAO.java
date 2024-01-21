package com.hms.www.repository;

import java.util.List;

import com.hms.www.security.AuthVO;
import com.hms.www.security.MemberVO;

public interface MemberDAO {

	int insertUser(MemberVO mvo);

	int insertAuth(String email);

	int selectEmail(String email);

	MemberVO selectUserName(String username);

	List<AuthVO> selectAuths(String username);

	boolean updateLastLogin(String authEmail);

}
