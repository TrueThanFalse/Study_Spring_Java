package com.hms.www.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hms.www.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthMemberService implements UserDetailsService {

	@Inject
	private MemberDAO mdao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// UserDetails : 실제 DB와 연결되는 객체
		
		// 매개변수로 들어온 username이 DB에 등록되어 있는 email과 일치되는 것이 있는지 체크(회원이 맞는지 체크)
		// -> 체크하여 일치하는 email이 있다면 해당 email 사용자를 AuthMember 객체로 반환
		// username은 http.formLogin() Method의 usernameParameter
		
		MemberVO mvo = mdao.selectUserName(username);
		
		if(mvo == null) {
			throw new UsernameNotFoundException(username);
			// Security에서 제공하는 Exception
		}
		
		mvo.setAuthList(mdao.selectAuths(username));
		
		log.info("@@@@@ UserDetails @@@ " + mvo);
		return new AuthMember(mvo);
	}

}
