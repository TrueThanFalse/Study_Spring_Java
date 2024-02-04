package com.hms.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.www.repository.MemberDAO;
import com.hms.www.security.AuthVO;
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
	public boolean updateLastLogin(String authEmail) {
		return mdao.updateLastLogin(authEmail);
	}

	@Override
	@Transactional
	public List<MemberVO> selectMemberList() {
		List<MemberVO> memberList = mdao.selectMemberList();
		for(int i=0; i<memberList.size(); i++) {
			String email = memberList.get(i).getEmail();
			List<AuthVO> authList = mdao.selectAuths(email);
			memberList.get(i).setAuthList(authList);
		};
		return memberList;
	}
	
	@Override
	public void noPwdUpdate(MemberVO mvo) {
		mdao.updateNopwd(mvo);
	}

	@Override
	public void pwdUpdate(MemberVO mvo) {
		mdao.updatePwd(mvo);
	}

	@Override
	public int Withdrawal(String email) {
		mdao.deleteAuthMember(email);
		return mdao.deleteMember(email);
	}
	
	@Override
	@Transactional
	public MemberVO detail(String email) {
		// MemberVO는 항상 authList와 한 세트라는 것을 잊지 말아야 함
		MemberVO mvo = mdao.selectEmail(email);
		List<AuthVO> authList = mdao.selectAuths(email);
		mvo.setAuthList(authList);
		return mvo;
	}

	@Override
	public int selectEmail(String email) {
		return mdao.selectEmailInt(email);
	}
}
