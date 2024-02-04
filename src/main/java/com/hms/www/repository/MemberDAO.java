package com.hms.www.repository;

import java.util.List;

import com.hms.www.security.AuthVO;
import com.hms.www.security.MemberVO;

public interface MemberDAO {

	int insertUser(MemberVO mvo);

	int insertAuth(String email);

	MemberVO selectEmail(String username);

	MemberVO selectUserName(String username);

	List<AuthVO> selectAuths(String username);

	boolean updateLastLogin(String authEmail);

	List<MemberVO> selectMemberList();
	
	void updateNopwd(MemberVO mvo);

	void updatePwd(MemberVO mvo);

	void deleteAuthMember(String email);

	int deleteMember(String email);

	int selectEmailInt(String email);

}
