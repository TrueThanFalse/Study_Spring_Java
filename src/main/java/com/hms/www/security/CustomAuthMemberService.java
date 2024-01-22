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
		// -> 체크하여 일치하는 email이 있다면 해당 mvo를 완성시켜서 AuthMember 생성자를 실행
		// 참고 : username은 http.formLogin() Method의 usernameParameter에서 가져오는 값
		
		MemberVO mvo = mdao.selectUserName(username);
		// http.formLogin() Method의 usernameParameter을 DB와 비교하여 일치하는 mvo 가져오기
		
		if(mvo == null) {
			// 만약 로그인 시도한 email과 DB에 등록된 email 중에 일치하는 것이 없다면 Exception 발생시키기
			throw new UsernameNotFoundException(username);
			// Security에서 제공하는 Exception
		}
		
		mvo.setAuthList(mdao.selectAuths(username));
		// authList까지 세팅되면 현재 mvo의 멤버변수는 모두 세팅된 상태가 된다.
		// 그 mvo를 AuthMember 생성자에 넣으면 Authentication의 User와 principal이 세팅됨
		
		log.info("@@@@@ UserDetails @@@ " + mvo);
		return new AuthMember(mvo);
	}

}
