package com.hms.www.service;

import java.util.List;

import com.hms.www.security.MemberVO;

public interface MemberService {

	int signUP(MemberVO mvo);

	boolean updateLastLogin(String authEmail);

	List<MemberVO> selectMemberList();
	
	void noPwdUpdate(MemberVO mvo);

	void pwdUpdate(MemberVO mvo);

	int Withdrawal(String email);
	
	MemberVO detail(String email);

	int selectEmail(String email);

}