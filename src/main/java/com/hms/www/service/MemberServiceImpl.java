package com.hms.www.service;

import org.springframework.stereotype.Service;

import com.hms.www.repository.MemberDAO;
import com.hms.www.security.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberDAO mdao;

	@Override
	public int signUP(MemberVO mvo) {
		/*
		 * 회원가입할 때 MemverVO만 가입하는 것이 아니라
		 * MemberVO 멤버변수인 권한(AuthVO)도 같이 부여해야 한다.
		 * 따라서 MemberVO를 등록한 후에 AuthVO도 등록해줘야 함
		 */
		mdao.insertUser(mvo);
		return mdao.insertAuth(mvo.getEmail());
		// 권한 부여의 기본값은 User로 Setting 하고
		// ADMIN 권한은 DB에서 직접 부여하여 관리하는 것이 효율적이다.
	}

	@Override
	public int selectEmail(String email) {
		return mdao.selectEmail(email);
	}

	@Override
	public boolean updateLastLogin(String authEmail) {
		return mdao.updateLastLogin(authEmail);
	}
}
