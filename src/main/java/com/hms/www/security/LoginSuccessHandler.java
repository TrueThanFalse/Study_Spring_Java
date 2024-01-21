package com.hms.www.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.hms.www.service.MemberService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Getter
	@Setter
	private String authEmail;
	/*
	 * Lombok의 기능인 Getter, Setter 어노테이션은 클래스가 아닌
	 * 각각의 특정 멤버변수에 적용하여 활용할 수도 있다.
	 * 즉, 멤버변수 중 getter와 setter를 주고 싶지 않다면
	 * 특정 멤버변수에 어노테이션을 적용시키면 된다.
	 */
	
	@Getter
	@Setter
	private String authUrl; // 전송할 경로
	
	/* 
	 * RedirectStrategy : redirect 데이터를 가지고 리다이렉트 하는 객체
	 * 로그인 인증이 필요한 행위를 하면 http.authorizeRequests()의 authenticated()에 의하여
	 * 로그인 페이지로 이동이 되는데 로그인 인증이 필요한 행위를 어디에서 했는지 기억해두었다가
	 * 로그인 페이지에서 로그인을 하면 기억해두었던 경로로 다시 리다이렉트하는 역할을 한다.
	 */
	private RedirectStrategy rdstg = new DefaultRedirectStrategy();
	
	// 실제 로그인 정보, 경로 등을 Cache에 저장하는 객체
	private RequestCache reqCache = new HttpSessionRequestCache();
	
	@Inject
	private MemberService msv;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Authentication : 인증 완료된 객체(AuthMember 객체)
		
		setAuthEmail(authentication.getName()); // 실질적으로 AuthMember의 email (MemberVO의 email)
		setAuthUrl("/board/list");
		
		// 마지막 로그인 날짜 업데이트
		boolean isOK = msv.updateLastLogin(getAuthEmail());
		
		// 로그인 세션에 저장됨
		HttpSession ses = request.getSession();
		log.info("@@@@@ HttpSession @@@ ses : " + ses.toString());
		
		if(!isOK || ses == null) {
			// 로그인 실패된 경우
			return; // 현재 onAuthenticationSuccess Method를 종료
			// SecurityConfig의 http.formLogin() Method의 successHandler가 종료되고
			// failureHandler가 실행됨
		}else {
			// 로그인 성공인 경우
			/*
			 * Security에서 로그인을 시도하면 시도할 때마다 로그인을 시도를 했다는 기록이 남는다.
			 * 일반적으로 보안이 중요한 웹사이트의 경우 로그인 시도가 일정 수를 넘어가면
			 * 로그인 시도를 몇분 간 막아버리거나 이미지 선택 등의 기계의 공격이 아닌 사람인지 체크 등의
			 * 중간 인증 절차를 삽입하여 보안성을 확보하고 있다.
			 * 따라서 로그인에 성공했다면 이전의 로그인 시도 기록을 초기화 시켜줘야 한다.
			 * => Security의 인증 실패 기록을 삭제
			 */
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		// SavedRequest의 import : org.springframework.security.web.savedrequest.SavedRequest
		SavedRequest saveReq = reqCache.getRequest(request, response);
		rdstg.sendRedirect(request, response, (saveReq != null ? saveReq.getRedirectUrl() : getAuthUrl()));
	}

}
