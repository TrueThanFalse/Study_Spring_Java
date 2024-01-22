package com.hms.www.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class AuthMember extends User{
	// 우선 org.springframework.security.core.userdetails의 User를 상속 받는다.
	
	// 상속 받으면 AuthMember이 빨간줄 나오는데 add create로 Method를 상속받아 자동 생성한다.
	public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	// 위 Method를 자동 생성하면 AuthMember에 노란줄이 생기는데
	// add default serial version ID 클릭하여 자동 생성한다.
	private static final long serialVersionUID = 1L;
	
	// MemberVO 객체 선언
	private MemberVO mvo;
	// => 이 MemberVO 객체가 principal에 들어감
	
	// 실제로 사용할 생성자 작성
	public AuthMember(MemberVO mvo) {
		super(mvo.getEmail(), mvo.getPwd(),
				mvo.getAuthList()
				.stream()
				.map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
				.collect(Collectors.toList())
				);
		// 위 super는 User를 세팅하는 Logic이고 오직 Authentication객체에 인증만을 위한 User 객체를 만든다.
		
		this.mvo = mvo;
	}
}
